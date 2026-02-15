#!/bin/bash
#
# Elasticsearch 레시피 인덱스 관리 스크립트
#
# 사용법:
#   ./scripts/es-index.sh create   - 인덱스 생성
#   ./scripts/es-index.sh delete   - 인덱스 삭제
#   ./scripts/es-index.sh recreate - 인덱스 삭제 후 재생성
#   ./scripts/es-index.sh status   - 인덱스 상태 확인
#

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

ES_HOST="${ES_HOST:-http://localhost:9200}"
INDEX_NAME="recipes"
INDEX_JSON="$PROJECT_ROOT/elasticsearch/index/recipe-index.json"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

log_info()  { echo -e "${GREEN}[INFO]${NC} $1"; }
log_warn()  { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

check_es() {
    if ! curl -s --fail "$ES_HOST" > /dev/null 2>&1; then
        log_error "Elasticsearch에 연결할 수 없습니다: $ES_HOST"
        log_error "Elasticsearch가 실행 중인지 확인하세요. (docker-compose up -d)"
        exit 1
    fi
    log_info "Elasticsearch 연결 확인: $ES_HOST"
}

check_index_json() {
    if [ ! -f "$INDEX_JSON" ]; then
        log_error "인덱스 설정 파일을 찾을 수 없습니다: $INDEX_JSON"
        exit 1
    fi
}

index_exists() {
    curl -s -o /dev/null -w "%{http_code}" "$ES_HOST/$INDEX_NAME" | grep -q "200"
}

create_index() {
    check_index_json

    if index_exists; then
        log_warn "'$INDEX_NAME' 인덱스가 이미 존재합니다. 재생성하려면 'recreate' 명령을 사용하세요."
        return 1
    fi

    log_info "'$INDEX_NAME' 인덱스를 생성합니다..."

    RESPONSE=$(curl -s -w "\n%{http_code}" -X PUT "$ES_HOST/$INDEX_NAME" \
        -H "Content-Type: application/json" \
        -d @"$INDEX_JSON")

    HTTP_CODE=$(echo "$RESPONSE" | tail -1)
    BODY=$(echo "$RESPONSE" | sed '$d')

    if [ "$HTTP_CODE" = "200" ]; then
        log_info "'$INDEX_NAME' 인덱스가 성공적으로 생성되었습니다."
    else
        log_error "인덱스 생성 실패 (HTTP $HTTP_CODE)"
        echo "$BODY" | python3 -m json.tool 2>/dev/null || echo "$BODY"
        return 1
    fi
}

delete_index() {
    if ! index_exists; then
        log_warn "'$INDEX_NAME' 인덱스가 존재하지 않습니다."
        return 0
    fi

    log_info "'$INDEX_NAME' 인덱스를 삭제합니다..."

    RESPONSE=$(curl -s -w "\n%{http_code}" -X DELETE "$ES_HOST/$INDEX_NAME")

    HTTP_CODE=$(echo "$RESPONSE" | tail -1)
    BODY=$(echo "$RESPONSE" | sed '$d')

    if [ "$HTTP_CODE" = "200" ]; then
        log_info "'$INDEX_NAME' 인덱스가 삭제되었습니다."
    else
        log_error "인덱스 삭제 실패 (HTTP $HTTP_CODE)"
        echo "$BODY" | python3 -m json.tool 2>/dev/null || echo "$BODY"
        return 1
    fi
}

recreate_index() {
    log_info "인덱스를 재생성합니다..."
    delete_index
    create_index
}

show_status() {
    if ! index_exists; then
        log_warn "'$INDEX_NAME' 인덱스가 존재하지 않습니다."
        return 0
    fi

    log_info "'$INDEX_NAME' 인덱스 상태:"
    echo ""

    # 문서 수
    DOC_COUNT=$(curl -s "$ES_HOST/$INDEX_NAME/_count" | python3 -c "import sys,json; print(json.load(sys.stdin).get('count', 'N/A'))" 2>/dev/null || echo "N/A")
    echo "  문서 수: $DOC_COUNT"

    # 인덱스 크기
    STORE_SIZE=$(curl -s "$ES_HOST/_cat/indices/$INDEX_NAME?h=store.size" | tr -d '[:space:]')
    echo "  인덱스 크기: ${STORE_SIZE:-N/A}"

    # 헬스
    HEALTH=$(curl -s "$ES_HOST/_cat/indices/$INDEX_NAME?h=health" | tr -d '[:space:]')
    echo "  헬스: ${HEALTH:-N/A}"
    echo ""
}

usage() {
    echo "Elasticsearch 레시피 인덱스 관리 스크립트"
    echo ""
    echo "사용법: $0 <command>"
    echo ""
    echo "Commands:"
    echo "  create    인덱스 생성 (recipe-index.json 기반)"
    echo "  delete    인덱스 삭제"
    echo "  recreate  인덱스 삭제 후 재생성"
    echo "  status    인덱스 상태 확인"
    echo ""
    echo "환경 변수:"
    echo "  ES_HOST   Elasticsearch 주소 (기본값: http://localhost:9200)"
    echo ""
    echo "예시:"
    echo "  $0 create"
    echo "  ES_HOST=http://es-server:9200 $0 recreate"
}

# --- Main ---

if [ $# -eq 0 ]; then
    usage
    exit 1
fi

check_es

case "$1" in
    create)   create_index ;;
    delete)   delete_index ;;
    recreate) recreate_index ;;
    status)   show_status ;;
    *)
        log_error "알 수 없는 명령: $1"
        echo ""
        usage
        exit 1
        ;;
esac
