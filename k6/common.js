import http from "k6/http";
import { check, sleep, fail } from "k6";

// 테스트 대상 서버
const BASE_URL = __ENV.BASE_URL || "http://dev.shortkki.kr";

// 인증 필요한 API 호출 시 사용할 Bearer 토큰 (없으면 비인증 호출)
const TOKEN = __ENV.TOKEN || "";

// 로그 추적용 테스트 ID (권장: exp-...-warmup / exp-...-main)
const TEST_ID = __ENV.TEST_ID || `exp-${Date.now()}-main`;

// 요청 타임아웃
const TIMEOUT = __ENV.TIMEOUT || "10s";

// 호출할 엔드포인트 목록 (쉼표 구분)
const ENDPOINTS = (__ENV.ENDPOINTS || "/actuator/health")
    .split(",")
    .map((v) => v.trim())
    .filter(Boolean);

// 외부 API 유발 가능 엔드포인트 차단 목록
const DENYLIST = (__ENV.DENYLIST || "/api/oauth,/api/auth,/api/push,/api/import,/api/parse,/api/files/uploads")
    .split(",")
    .map((v) => v.trim())
    .filter(Boolean);

// warm-up 모드 여부
const isWarmup = (__ENV.WARMUP || "false").toLowerCase() === "true";

// 안전장치: 금지 엔드포인트 포함 시 즉시 종료
for (const ep of ENDPOINTS) {
    for (const denied of DENYLIST) {
        if (denied && ep.includes(denied)) {
            fail(`Blocked endpoint by DENYLIST: ${ep} (matched: ${denied})`);
        }
    }
}

// 부하 시나리오
export const options = isWarmup
    ? {
        stages: [
            { duration: "30s", target: 10 },
            { duration: "10s", target: 0 },
        ],
        thresholds: { http_req_failed: ["rate<0.05"] },
    }
    : {
        stages: [
            { duration: "1m", target: 20 },
            { duration: "3m", target: 20 },
            { duration: "1m", target: 0 },
        ],
        thresholds: {
            http_req_failed: ["rate<0.01"],
            http_req_duration: ["p(95)<700", "p(99)<1200"],
        },
    };

// 공통 헤더
function headers() {
    const h = {
        "Content-Type": "application/json",
        "X-Test-Id": TEST_ID,
        "X-Perf-Test": "true",
    };
    if (TOKEN) h.Authorization = `Bearer ${TOKEN}`;
    return h;
}

export default function () {
    for (const ep of ENDPOINTS) {
        const res = http.get(`${BASE_URL}${ep}`, {
            headers: headers(),
            tags: { name: ep }, // endpoint별 메트릭 분리
            timeout: TIMEOUT,
        });

        check(res, {
            [`${ep} status is 200/204`]: (r) => r.status === 200 || r.status === 204,
        });
    }

    sleep(1);
}

