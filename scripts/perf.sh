#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
K6_SCRIPT="${K6_SCRIPT:-$ROOT_DIR/k6/common.js}"
BASE_URL="${BASE_URL:-http://dev.shortkki.kr}"
ENDPOINTS="${ENDPOINTS:-/actuator/health}"
DENYLIST="${DENYLIST:-/api/oauth,/api/auth,/api/push,/api/import,/api/parse,/api/files/uploads}"
TIMEOUT="${TIMEOUT:-10s}"
EXPERIMENT_ID="${EXPERIMENT_ID:-exp-$(date +%Y%m%d-%H%M%S)}"
TOKEN="${TOKEN:-}"
WARMUP="${WARMUP:-false}"
MEMBER_ID=""

usage() {
  cat <<USAGE
Usage: ./scripts/perf.sh [options]

Options:
  --script PATH          k6 script path (default: k6/common.js)
  --base-url URL         target base URL
  --endpoints CSV        endpoints CSV
  --denylist CSV         blocked endpoints CSV
  --timeout VALUE        request timeout (default: 10s)
  --token VALUE          bearer token
  --token-member ID      issue token via /api/dev/tokens?memberId=ID
  --test-id VALUE        experiment id (exp-xxxx). suffix is auto-added.
  --warmup               run warmup mode
  -h, --help             show help
USAGE
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --script) K6_SCRIPT="$2"; shift 2 ;;
    --base-url) BASE_URL="$2"; shift 2 ;;
    --endpoints) ENDPOINTS="$2"; shift 2 ;;
    --denylist) DENYLIST="$2"; shift 2 ;;
    --timeout) TIMEOUT="$2"; shift 2 ;;
    --token) TOKEN="$2"; shift 2 ;;
    --token-member) MEMBER_ID="$2"; shift 2 ;;
    --test-id) EXPERIMENT_ID="$2"; shift 2 ;;
    --warmup) WARMUP="true"; shift ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown option: $1"; usage; exit 1 ;;
  esac
done

if [[ ! -f "$K6_SCRIPT" ]]; then
  echo "k6 script not found: $K6_SCRIPT"
  exit 1
fi

if [[ -n "$MEMBER_ID" ]]; then
  if [[ -n "$TOKEN" ]]; then
    echo "WARN: --token-member is set. Existing TOKEN will be overwritten."
  fi

  echo "Issuing token for memberId=$MEMBER_ID from $BASE_URL"
  HTTP_CODE="$(curl -sS -o /tmp/perf_token_resp.json -w "%{http_code}" \
    --connect-timeout 3 --max-time 10 \
    -X POST "$BASE_URL/api/dev/tokens?memberId=$MEMBER_ID")"

  TOKEN_RESPONSE="$(cat /tmp/perf_token_resp.json)"
  if [[ "$HTTP_CODE" != "200" ]]; then
    echo "Token API failed (status=$HTTP_CODE)"
    echo "$TOKEN_RESPONSE"
    exit 1
  fi

  if command -v jq >/dev/null 2>&1; then
    TOKEN="$(printf '%s' "$TOKEN_RESPONSE" | jq -r '.data.accessToken // empty')"
  else
    TOKEN="$(printf '%s' "$TOKEN_RESPONSE" | sed -n 's/.*"accessToken":"\([^"]*\)".*/\1/p')"
  fi

  if [[ -z "$TOKEN" ]]; then
    echo "Failed to extract access token from response"
    echo "$TOKEN_RESPONSE"
    exit 1
  fi
fi

if [[ "$WARMUP" == "true" ]]; then
  TEST_ID="${EXPERIMENT_ID}-warmup"
else
  TEST_ID="${EXPERIMENT_ID}-main"
fi

echo "Running k6"
echo "- script:       $K6_SCRIPT"
echo "- baseUrl:      $BASE_URL"
echo "- warmup:       $WARMUP"
echo "- experimentId: $EXPERIMENT_ID"
echo "- testId:       $TEST_ID"
echo "- endpoints:    $ENDPOINTS"

docker run --rm -i \
  -e BASE_URL="$BASE_URL" \
  -e TOKEN="$TOKEN" \
  -e TEST_ID="$TEST_ID" \
  -e ENDPOINTS="$ENDPOINTS" \
  -e DENYLIST="$DENYLIST" \
  -e TIMEOUT="$TIMEOUT" \
  -e WARMUP="$WARMUP" \
  -v "$ROOT_DIR:/work" \
  grafana/k6 run "/work/${K6_SCRIPT#$ROOT_DIR/}"
