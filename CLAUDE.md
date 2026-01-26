# CLAUDE.md

이 파일은 Claude Code (claude.ai/code)가 이 저장소의 코드를 작업할 때 참고하는 가이드입니다.

## 빌드 & 테스트 명령어

```bash
# 빌드
./gradlew build

# 테스트 실행
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "com.shortkki.test.smoke.DBConnectionTest"

# 단일 테스트 메서드 실행
./gradlew test --tests "com.shortkki.test.smoke.DBConnectionTest.testMethod"

# 애플리케이션 실행 (기본적으로 local 프로필 사용)
./gradlew bootRun

# JaCoCo 커버리지 리포트 생성
./gradlew jacocoTestReport
```

## 아키텍처

### 패키지 구조
- `com.shortkki.api.*` - 도메인 모듈 (auth, feed, file, group, ingredient, member, recipe, shopping_list)
- `com.shortkki.global.*` - 공통 관심사 (auth, config, entity, error, response)

### 도메인 모듈 패턴
각 도메인 모듈은 다음 구조를 따름:
- `controller/` - REST 엔드포인트
- `service/` - 비즈니스 로직
- `repository/` - 데이터 접근 (JPA + QueryDSL 커스텀 구현체)
- `entity/` - JPA 엔티티
- `dto/request/`, `dto/response/` - 요청/응답 DTO

### Repository 패턴
Repository는 Spring Data JPA와 QueryDSL을 함께 사용:
- `*Repository` - JpaRepository 상속
- `*RepositoryCustom` - QueryDSL 메서드용 인터페이스
- `*RepositoryImpl` - QueryDSL 구현체

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
- `LoginMember` - 인증된 사용자를 위한 시큐리티 컨텍스트 홀더
- `@WithMockMember` - 테스트에서 인증된 사용자를 모킹하는 어노테이션

### 파일 업로드
- AWS S3에 Presigned URL 기반 업로드
- `FileUploadPort` 인터페이스와 `S3FileUploadAdapter` 구현체
- `FileMetadata`가 업로드 상태와 공개 여부 추적

## 테스트

### 테스트 기반 클래스
- `IntegrationTestBase` - Testcontainers MySQL 사용, MockMvc와 ObjectMapper 제공
- `UnitTestBase` - strict stubs 설정의 Mockito 사용

### 테스트 프로필
테스트는 `@ActiveProfiles("test")`로 H2 또는 Testcontainers MySQL 사용.

## 주요 컨벤션

- Java 21, Spring Boot 3.5.9
- 엔티티는 protected 기본 생성자와 `@Builder`, 정적 팩토리 메서드 (`create()`) 사용
- QueryDSL 생성 소스는 `src/main/generated/`에 위치
- Flyway 마이그레이션은 `src/main/resources/db/migration/`에 위치
- 설정 속성은 `@ConfigurationProperties`로 바인딩 (예: `FileUploadProperties`, `OAuth2Properties`)
