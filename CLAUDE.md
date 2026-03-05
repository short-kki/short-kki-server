# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 로컬 개발 환경

```bash
# 로컬 인프라 실행 (MySQL:3306, Elasticsearch:9200, Kibana:5601, nGrinder:80)
docker-compose up -d

# 애플리케이션 실행 (기본적으로 local 프로필 사용)
./gradlew bootRun
```

- API 문서: http://localhost:8080/swagger-ui/index.html
- 로컬 환경 변수는 `.env.local` 파일에 설정 (`spring.config.import`로 자동 로드)
- AWS 키, Google API 키 등 민감 정보는 `.env.local`이나 환경변수로 주입

### 프로필 구성
- `local` (기본) - 로컬 MySQL + Elasticsearch, `application-local.yaml`
- `dev` - 개발 서버, `application-dev.yaml`
- `test` - Testcontainers MySQL/ES 사용, `application-test.yaml`

## 빌드 & 테스트 명령어

```bash
# 빌드
./gradlew build

# 컴파일만 확인 (빠른 검증)
./gradlew compileJava

# 테스트 실행
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "com.shortkki.test.smoke.DBConnectionTest"

# 단일 테스트 메서드 실행
./gradlew test --tests "com.shortkki.test.smoke.DBConnectionTest.testMethod"

# JaCoCo 커버리지 리포트 생성
./gradlew jacocoTestReport
```

## 아키텍처

### 패키지 구조
- `com.shortkki.api.*` - 도메인 모듈 (auth, calendar, curation, feed, file, group, ingredient, member, publicdata, recipe, recipeBook, recipeImport, search, shopping_list, source)
- `com.shortkki.global.*` - 공통 관심사 (auth, config, entity, error, event, response, utils)

### 도메인 모듈 패턴
각 도메인 모듈은 다음 구조를 따름:
- `controller/` - REST 엔드포인트, `BaseResponse`로 래핑하여 반환
- `service/` - CQRS-lite: `*QueryService`는 조회 전용, `*Service`는 명령(생성/수정/삭제)
- `repository/` - 데이터 접근 (JPA + QueryDSL 커스텀 구현체)
- `entity/` 또는 `domain/` - JPA 엔티티
- `dto/request/`, `dto/response/` - 요청/응답 DTO

일부 모듈은 헥사고날 아키텍처 적용:
- `application/port/` - 추상화 인터페이스
- `application/service/` 또는 `application/usecase/` - 유스케이스 구현
- `infra/` - 어댑터 구현체 (예: `ESRecipeSearchAdapter`, `S3FileUploadAdapter`)

### Repository 패턴
Repository는 Spring Data JPA와 QueryDSL을 함께 사용:
- `*Repository` - JpaRepository 상속
- `*RepositoryCustom` - QueryDSL 메서드용 인터페이스
- `*RepositoryImpl` - QueryDSL 구현체

### 데이터베이스 스키마 관리
- **Flyway**로 DDL 관리, JPA는 `ddl-auto: validate`로 검증만 수행
- 마이그레이션 파일: `src/main/resources/db/migration/V{버전}__{설명}.sql`
- 새 테이블/컬럼 추가 시 반드시 Flyway 마이그레이션 파일 작성 필요

### 주요 도메인 관계
- `Member` - 사용자, OAuth 로그인으로 가입
- `Group` - 그룹, `GroupMember`를 통해 Member와 N:M 관계
- `InviteLink` - 초대 링크, Group과 1:N 관계 (만료일 기반 유효성 검증)
- `Recipe` - 레시피, `RecipeIngredient`를 통해 Ingredient와 N:M 관계
- `RecipeBook` - 레시피북, Member당 기본 레시피북 존재, `RecipeBookEntry`로 Recipe와 N:M 관계
- `ShoppingList` - 장볼거리, Group과 Ingredient에 속함
- `SourceContent` - 외부 레시피 원본 정보 (YouTube 등), `SourceContentCreator`로 크리에이터 관리
- `Feed` - 피드, Recipe를 참조
- `RecipeCalendar`, `RecipeQueue` - 식단 캘린더 및 대기열 관리
- `Curation` - 레시피 큐레이션 (TimeType, DayType 등 조건 기반)

### 기반 클래스
- `BaseEntity` - 모든 엔티티에 `createdAt`, `updatedAt` 감사 필드 제공
- `BaseResponse<T>` - `code`, `message`, `data`를 포함하는 표준 API 응답 래퍼

### 예외 처리
- `BusinessException` - `ErrorCode`를 받는 기본 예외 클래스
- `ErrorCode` - HTTP 상태와 메시지를 정의하는 에러 코드 enum
- `GlobalExceptionHandler` - `BaseResponse`를 반환하는 중앙 집중식 예외 처리
- 예외 클래스: `NotFoundException`, `BadRequestException`, `ConflictException`, `AccessDeniedException`, `InternalServerException`, `InvalidStateException`, `NotImplementedException`

### 인증
- `JwtAuthenticationFilter`를 통한 JWT 기반 무상태 인증
- Google (iOS/Android/Web), Kakao, Naver OAuth2 로그인 지원
- `LoginMember` - `UserDetails` 구현체, 인증된 사용자 정보를 담는 DTO
- 공개 엔드포인트: `/api/auth/**`, `/api/v1/groups/invite/**`, `/api/v1/health`, Swagger UI

### 파일 업로드
- AWS S3에 Presigned URL 기반 업로드
- `FileUploadPort` 인터페이스와 `S3FileUploadAdapter` 구현체
- `FileMetadata`가 업로드 상태와 공개 여부 추적

### 레시피 가져오기 (recipeImport)
- Google Gemini AI를 사용한 외부 레시피 파싱 (`GeminiRecipeParserAdapter`)
- `RecipeParserPort` 인터페이스로 파서 추상화
- 비동기 처리: `RecipeImportAsyncService`로 긴 작업 백그라운드 실행

### 외부 콘텐츠 (source)
- `SourceDataProvider` 인터페이스와 플랫폼별 구현체 (예: `YoutubeSourceDataProvider`)
- `ExternalKeyExtractorRegistry`로 URL에서 플랫폼 감지 및 외부 키 추출
- `SourceImportHistory`로 가져오기 이력 추적

### 검색 (search)
- Elasticsearch 기반 레시피 검색 (`RecipeSearchPort` → `ESRecipeSearchAdapter`)
- JPA 폴백 구현체 (`JpaRecipeSearchAdapter`)
- Spring Event로 인덱스 동기화 (`RecipeIndexUpsertEvent` → `RecipeIndexEventHandler`)

### 이벤트 시스템
- `DomainEventPublisher` 인터페이스와 `InProcessEventPublisher` 구현
- Spring `ApplicationEventPublisher`를 래핑하여 도메인 이벤트 발행

## 테스트

### 테스트 기반 클래스
- `IntegrationTestBase` - `@ServiceConnection`으로 Testcontainers MySQL + Elasticsearch 자동 관리, MockMvc/ObjectMapper/JdbcTemplate 제공. 정적 컨테이너로 테스트 클래스 간 재사용
- `UnitTestBase` - `STRICT_STUBS` 모드의 Mockito (미사용 stub은 에러). Spring 컨텍스트 미로드
- `@WithMockMember` - 커스텀 인증 모킹. 커스터마이징: `@WithMockMember(id=2L, email="test@test.com", role=Role.ADMIN)`

### 테스트 프로필
테스트는 `@ActiveProfiles("test")`로 Testcontainers MySQL/Elasticsearch 사용. `ddl-auto: none` (Flyway가 스키마 관리).

## CI/CD

### GitHub Actions
- **CI (`ci.yml`):** push/PR → main/develop에서 트리거. JDK 21 빌드+테스트, JaCoCo 커버리지 리포트 PR 코멘트
- **CD Dev (`cd-dev.yml`):** develop push → Docker 이미지(linux/arm64) 빌드 → GHCR push → EC2 배포
- **CD Prod (`cd-prod.yml`):** main push → 프로덕션 배포
- 배포 스크립트: `scripts/deploy-*.sh`

## 모니터링

- Actuator 엔드포인트: `/actuator/health`, `/actuator/prometheus`
- Micrometer Prometheus 메트릭 (application tag: `short-kki`)
- 모니터링 스택: `monitoring/docker-compose.yml` (Prometheus:9090, Grafana:3000)

## Git 컨벤션

### 커밋 메시지
- 형식: `{type}: {설명}` (예: `refactor: GroupService 북마크 보정 N+1 쿼리 배치 조회로 개선`)
- type: `feat`, `fix`, `refactor`, `docs`, `test`, `chore` 등
- 이슈 번호 참조: `(#{이슈번호})` 접미사 (예: `(#86)`)

### 브랜치 네이밍
- `feat/#{이슈번호}-설명`, `fix/#{이슈번호}-설명`, `refactor/#{이슈번호}-설명`
- PR 대상 브랜치: `develop`

## 주요 컨벤션

- Java 21, Spring Boot 3.5.9
- 엔티티는 protected 기본 생성자 + `@Builder` (private) + 정적 팩토리 메서드 (`create()`) 사용
- QueryDSL 생성 소스는 `src/main/generated/`에 위치 (`clean` 시 삭제됨)
- 설정 속성은 `@ConfigurationProperties`로 바인딩 (예: `FileUploadProperties`, `OAuth2Properties`)
- 예외는 `ErrorCode` enum에 정의 후 적절한 예외 클래스 사용
- JPA `open-in-view: false`, `default_batch_fetch_size: 100` 설정
- 검증 로직은 `*ValidationService` 클래스로 분리 (예: `GroupValidationService`, `RecipeBookValidationService`)
