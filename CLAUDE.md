# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 로컬 개발 환경

```bash
# 로컬 인프라 실행 (MySQL:3307, Elasticsearch:9200, Kibana:5601)
docker-compose up -d

# 애플리케이션 실행 (기본적으로 local 프로필 사용)
./gradlew bootRun
```

API 문서: http://localhost:8080/swagger-ui/index.html

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
- `controller/` - REST 엔드포인트
- `service/` - 비즈니스 로직 (`*QueryService`는 조회, `*Service`는 명령)
- `repository/` - 데이터 접근 (JPA + QueryDSL 커스텀 구현체)
- `entity/` 또는 `domain/` - JPA 엔티티
- `dto/request/`, `dto/response/` - 요청/응답 DTO

일부 모듈은 헥사고날 아키텍처 적용:
- `application/port/` - 추상화 인터페이스
- `application/service/` - 유스케이스 구현
- `infra/` - 어댑터 구현체 (예: `ESRecipeSearchAdapter`, `S3FileUploadAdapter`)

### Repository 패턴
Repository는 Spring Data JPA와 QueryDSL을 함께 사용:
- `*Repository` - JpaRepository 상속
- `*RepositoryCustom` - QueryDSL 메서드용 인터페이스
- `*RepositoryImpl` - QueryDSL 구현체

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

### 인증
- `JwtAuthenticationFilter`를 통한 JWT 기반 무상태 인증
- Google (iOS/Android), Kakao, Naver OAuth2 로그인 지원
- `LoginMember` - `UserDetails` 구현체, 인증된 사용자 정보를 담는 DTO
- `@WithMockMember` - 테스트에서 인증된 사용자를 모킹하는 어노테이션

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

## 테스트

### 테스트 기반 클래스
- `IntegrationTestBase` - Testcontainers MySQL 사용, MockMvc와 ObjectMapper 제공
- `UnitTestBase` - strict stubs 설정의 Mockito 사용

### 테스트 프로필
테스트는 `@ActiveProfiles("test")`로 H2 또는 Testcontainers MySQL 사용.

### 이벤트 시스템
- `DomainEventPublisher` 인터페이스와 `InProcessEventPublisher` 구현
- Spring `ApplicationEventPublisher`를 래핑하여 도메인 이벤트 발행

## 주요 컨벤션

- Java 21, Spring Boot 3.5.9
- 엔티티는 protected 기본 생성자와 `@Builder`, 정적 팩토리 메서드 (`create()`) 사용
- QueryDSL 생성 소스는 `src/main/generated/`에 위치
- 설정 속성은 `@ConfigurationProperties`로 바인딩 (예: `FileUploadProperties`, `OAuth2Properties`)
- 예외는 `ErrorCode` enum에 정의 후 적절한 예외 클래스 사용 (`NotFoundException`, `BadRequestException`, `ConflictException` 등)
