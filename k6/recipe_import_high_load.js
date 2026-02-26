import http from "k6/http";
import { check, fail, sleep } from "k6";
import { Rate, Trend } from "k6/metrics";

// 테스트 대상 서버
const BASE_URL = __ENV.BASE_URL || "http://dev.shortkki.kr";
// Dev 토큰 (scripts/perf.sh --token-member 로 발급하거나 직접 전달)
const TOKEN = __ENV.TOKEN || "";
// 로그/대시보드 추적용 테스트 ID
const TEST_ID = __ENV.TEST_ID || `exp-${Date.now()}-import-high`;
// 요청 타임아웃
const TIMEOUT = __ENV.TIMEOUT || "10s";
// import 요청 URL
const SOURCE_URL = __ENV.SOURCE_URL || "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
// import 후 상태 조회 여부
const ENABLE_STATUS_POLL = (__ENV.ENABLE_STATUS_POLL || "true").toLowerCase() === "true";
const UNIQUE_SOURCE_PER_ITER = (__ENV.UNIQUE_SOURCE_PER_ITER || "true").toLowerCase() === "true";
const POLL_COUNT = Number(__ENV.POLL_COUNT || 2);
const POLL_INTERVAL_SECONDS = Number(__ENV.POLL_INTERVAL_SECONDS || 0.5);

// import enqueue API 기준 고부하 시나리오 (50 -> 100 -> 0)
export const options = {
  stages: [
    { duration: "2m", target: 50 },
    { duration: "4m", target: 100 },
    { duration: "2m", target: 0 },
  ],
  thresholds: {
    http_req_failed: ["rate<0.01"],
    http_req_duration: ["p(95)<1000", "p(99)<1500"],
  },
};

const serverErrorRate = new Rate("server_error_rate");
const importAcceptedRate = new Rate("import_accepted_rate");
const importLatency = new Trend("import_api_latency", true);
const statusLatency = new Trend("status_poll_latency", true);
const unauthorizedRate = new Rate("import_401_rate");
const forbiddenRate = new Rate("import_403_rate");
const notFoundRate = new Rate("import_404_rate");
const tooManyRequestsRate = new Rate("import_429_rate");
const timeoutRate = new Rate("import_timeout_rate");
const other4xxRate = new Rate("import_other_4xx_rate");
const otherErrorRate = new Rate("import_other_error_rate");

function commonHeaders() {
  const h = {
    "Content-Type": "application/json",
    "X-Test-Id": TEST_ID,
    "X-Perf-Test": "true",
  };
  if (TOKEN) {
    h.Authorization = `Bearer ${TOKEN}`;
  }
  return h;
}

function buildSourceUrl() {
  if (!UNIQUE_SOURCE_PER_ITER) {
    return SOURCE_URL;
  }
  const videoId = `${__VU.toString(36)}${__ITER.toString(36)}xxxxxxxxxxx`.slice(0, 11);
  return `https://www.youtube.com/watch?v=${videoId}`;
}

export function setup() {
  if (UNIQUE_SOURCE_PER_ITER) {
    return;
  }

  const preflightRes = http.post(
    `${BASE_URL}/api/v1/recipe/import`,
    JSON.stringify({ sourceUrl: SOURCE_URL }),
    {
      headers: commonHeaders(),
      tags: { name: "import_preflight" },
      timeout: TIMEOUT,
    }
  );

  if (preflightRes.status !== 200 && preflightRes.status !== 202) {
    const body = (preflightRes.body || "").slice(0, 300);
    fail(
      `[preflight-fail] status=${preflightRes.status} error=${preflightRes.error || "none"} code=${preflightRes.error_code || "none"} body=${body}`
    );
  }
}

export default function () {
  const sourceUrl = buildSourceUrl();
  const importRes = http.post(
    `${BASE_URL}/api/v1/recipe/import`,
    JSON.stringify({ sourceUrl }),
    {
      headers: commonHeaders(),
      tags: { name: "import_enqueue" },
      timeout: TIMEOUT,
    }
  );
  importLatency.add(importRes.timings.duration);
  serverErrorRate.add(importRes.status >= 500);
  importAcceptedRate.add(importRes.status === 202);
  unauthorizedRate.add(importRes.status === 401);
  forbiddenRate.add(importRes.status === 403);
  notFoundRate.add(importRes.status === 404);
  tooManyRequestsRate.add(importRes.status === 429);
  timeoutRate.add(importRes.error_code === 1050 || importRes.error_code === 1211);
  other4xxRate.add(importRes.status >= 400 && importRes.status < 500
    && importRes.status !== 401
    && importRes.status !== 403
    && importRes.status !== 404
    && importRes.status !== 429);
  otherErrorRate.add(importRes.status === 0 || !!importRes.error);

  if (importRes.status !== 200 && importRes.status !== 202) {
    console.log(`[import-fail] status=${importRes.status} error=${importRes.error || "none"} code=${importRes.error_code || "none"} body=${(importRes.body || "").slice(0, 200)}`);
  }

  check(importRes, {
    "import api is 200 or 202": (r) => r.status === 200 || r.status === 202,
  });

  if (ENABLE_STATUS_POLL && importRes.status === 202) {
    let historyId = null;
    try {
      const body = importRes.json();
      historyId = body?.data?.importHistoryId ?? null;
    } catch (_) {
      historyId = null;
    }

    if (historyId) {
      for (let i = 0; i < POLL_COUNT; i += 1) {
        const statusRes = http.get(
          `${BASE_URL}/api/v1/recipe/import/${historyId}`,
          {
            headers: commonHeaders(),
            tags: { name: "import_status_poll" },
            timeout: TIMEOUT,
          }
        );
        statusLatency.add(statusRes.timings.duration);
        serverErrorRate.add(statusRes.status >= 500);
        check(statusRes, {
          "status api is not 5xx": (r) => r.status < 500,
        });
        sleep(POLL_INTERVAL_SECONDS);
      }
    }
  }

  sleep(1);
}
