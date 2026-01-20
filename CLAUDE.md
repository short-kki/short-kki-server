# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Short-Kki (숏끼) backend server - A Spring Boot application for group management and feed sharing. Groups can be created for couples, families, friends with features like invite codes, member management, and feeds.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application (uses local profile with H2 by default)
./gradlew bootRun

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.example.short_kki.SomeTest"

# Clean build (also removes QueryDSL generated sources)
./gradlew clean
```

## Tech Stack

- **Java 21** with Spring Boot 3.5.9
- **Database**: MySQL (prod), H2 in-memory (local)
- **ORM**: Spring Data JPA with QueryDSL 5.1.0 (Jakarta)
- **Auth**: JWT (JJWT 0.12.6) + OAuth2 (Google, Kakao, Naver)
- **Migration**: Flyway
- **Build**: Gradle

## Architecture

### Package Structure (Domain-Driven Design)

```
src/main/java/com/example/short_kki/
├── domain/
│   ├── auth/       # OAuth2 login, token refresh
│   ├── feed/       # Group feeds (posts)
│   ├── group/      # Core domain: groups, membership, roles
│   └── member/     # User accounts
└── global/
    ├── auth/       # JWT provider, security filter
    ├── config/     # Spring configs (Security, QueryDSL, JPA)
    ├── entity/     # BaseEntity (audit fields)
    ├── exception/  # BusinessException, ErrorCode, GlobalExceptionHandler
    ├── response/   # BaseResponse wrapper
    └── utils/      # CodeGenerator (invite codes)
```

### Key Patterns

- **Layered Architecture**: Controller → Service → Repository
- **Entity Factory Methods**: Use `Entity.create()` static methods (e.g., `Group.create()`, `MemberGroup.createAdmin()`)
- **Custom Repositories**: QueryDSL implementations in `*RepositoryCustom` interfaces
- **Centralized Exception Handling**: All business errors use `ErrorCode` enum with `BusinessException`
- **JWT Stateless Auth**: `JwtAuthenticationFilter` validates tokens, principal is `LoginMember`

### Entity Relationships

- **Group** ↔ **MemberGroup** (1:N) - Join table with role (ADMIN/MEMBER)
- **Group** ↔ **Feed** (1:N)
- **Member** ↔ **MemberGroup** (1:N)
- **Member** ↔ **Feed** (1:N) - Author relationship

### QueryDSL

Generated Q-classes are output to `src/main/generated/`. This directory is auto-cleaned on `./gradlew clean`.

## Configuration

- `application.yaml` - Base config (JPA, Flyway, OAuth2 endpoints)
- `application-local.yaml` - Local dev with H2, auto-create schema
- `application-dev.yaml` - Dev environment

Local profile uses `ddl-auto: create`. Production uses `ddl-auto: validate` with Flyway migrations.

## API Structure

Base path: `/api/v1`

- `/auth/**` - Public authentication endpoints
- `/groups/**` - Group management (requires auth)
  - Group CRUD, member list, feeds, invite codes
  - Role-based: some endpoints require ADMIN role in group

## Error Codes

Defined in `ErrorCode.java`. Group-related codes:
- `GROUP_001` - Group not found (404)
- `GROUP_002` - No access to group (403)
- `GROUP_003` - Admin required (403)
- `GROUP_004` - Already joined (409)
- `GROUP_005` - Invalid invite code (400)
- `GROUP_006` - Not a member (403)

## Documentation

Detailed API specs and feature status in `/docs/`:
- `group-api-development.md` - API endpoints and request/response formats
- `group-feature-analysis.md` - Implementation status and TODOs
