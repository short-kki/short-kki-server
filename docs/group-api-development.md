# Group 도메인 API 개발 문서

## 개요
그룹 생성, 관리, 참여 및 피드 기능을 제공하는 REST API 구현

## API 엔드포인트

| Method | Endpoint | 설명 | 권한 |
|--------|----------|------|------|
| POST | `/api/v1/groups` | 그룹 생성 | 인증 |
| PUT | `/api/v1/groups/{groupId}` | 그룹 수정 | ADMIN |
| DELETE | `/api/v1/groups/{groupId}` | 그룹 삭제 | ADMIN |
| GET | `/api/v1/groups/{groupId}` | 그룹 상세 조회 | 멤버 |
| GET | `/api/v1/groups/my` | 내 그룹 목록 | 인증 |
| GET | `/api/v1/groups/{groupId}/members` | 멤버 목록 조회 | 멤버 |
| GET | `/api/v1/groups/{groupId}/feeds` | 피드 목록 조회 | 멤버 |
| POST | `/api/v1/groups/{groupId}/feeds` | 피드 생성 | 멤버 |
| GET | `/api/v1/groups/{groupId}/shopping-list` | 장볼거리 조회 | 멤버 |
| DELETE | `/api/v1/groups/{groupId}/shopping-list` | 장볼거리 삭제 | ADMIN |
| POST | `/api/v1/groups/{groupId}/invite-code` | 초대코드 조회 | 멤버 |
| POST | `/api/v1/groups/join` | 그룹 참여 | 인증 |

---

## 파일 구조

```
src/main/java/com/example/short_kki/domain/
├── group/
│   ├── controller/
│   │   └── GroupController.java
│   ├── service/
│   │   └── GroupService.java
│   ├── entity/
│   │   ├── Group.java (기존)
│   │   ├── MemberGroup.java (수정)
│   │   └── GroupRole.java (신규)
│   ├── repository/
│   │   ├── GroupRepository.java (수정)
│   │   └── MemberGroupRepository.java (신규)
│   └── dto/
│       ├── request/
│       │   ├── CreateGroupRequest.java
│       │   ├── UpdateGroupRequest.java
│       │   ├── JoinGroupRequest.java
│       │   └── CreateFeedRequest.java
│       └── response/
│           ├── GroupResponse.java
│           ├── GroupListResponse.java
│           ├── GroupMemberResponse.java
│           └── InviteCodeResponse.java
├── feed/
│   ├── entity/
│   │   └── Feed.java (수정)
│   ├── repository/
│   │   └── FeedRepository.java (수정)
│   └── dto/
│       └── response/
│           └── FeedResponse.java
└── global/
    └── exception/
        └── ErrorCode.java (수정)
```

---

## Entity 변경사항

### MemberGroup (수정)
```java
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private GroupRole role;  // ADMIN, MEMBER

// 팩토리 메서드
public static MemberGroup createAdmin(Member member, Group group)
public static MemberGroup createMember(Member member, Group group)
public boolean isAdmin()
```

### Feed (수정)
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id", nullable = false)
private Member member;  // 작성자 필드 추가

public static Feed create(Group group, Member member, String content)
```

### GroupRole (신규)
```java
public enum GroupRole {
    ADMIN,
    MEMBER
}
```

---

## ErrorCode 추가

| 코드 | HTTP Status | 메시지 |
|------|-------------|--------|
| GROUP_001 | 404 | 그룹을 찾을 수 없습니다 |
| GROUP_002 | 403 | 그룹에 대한 접근 권한이 없습니다 |
| GROUP_003 | 403 | 그룹 관리자 권한이 필요합니다 |
| GROUP_004 | 409 | 이미 가입된 그룹입니다 |
| GROUP_005 | 400 | 유효하지 않은 초대 코드입니다 |
| GROUP_006 | 403 | 그룹 멤버가 아닙니다 |

---

## API 상세

### 1. 그룹 생성
**POST** `/api/v1/groups`

Request:
```json
{
  "name": "우리 가족",
  "description": "가족 그룹입니다",
  "thumbnailImgUrl": "https://example.com/image.jpg"
}
```

Response:
```json
{
  "code": "SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "id": 1,
    "name": "우리 가족",
    "description": "가족 그룹입니다",
    "thumbnailImgUrl": "https://example.com/image.jpg",
    "code": "A1B2C3D4",
    "memberCount": 1,
    "createdAt": "2024-01-01T12:00:00"
  }
}
```

### 2. 그룹 참여
**POST** `/api/v1/groups/join`

Request:
```json
{
  "inviteCode": "A1B2C3D4"
}
```

### 3. 피드 생성
**POST** `/api/v1/groups/{groupId}/feeds`

Request:
```json
{
  "content": "오늘 저녁 뭐 먹을까요?"
}
```

Response:
```json
{
  "code": "SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "id": 1,
    "content": "오늘 저녁 뭐 먹을까요?",
    "authorId": 1,
    "authorName": "홍길동",
    "createdAt": "2024-01-01T12:00:00"
  }
}
```

---

## 권한 검증 로직

```java
// 그룹 멤버 여부 확인
private void validateGroupMember(Long memberId, Group group)

// ADMIN 권한 확인
private void validateAdminRole(Long memberId, Group group)
```

---

## TODO

- [ ] 장보기(Shopping List) 테이블 설계 및 구현
- [ ] 피드 이미지/영상 첨부 기능
- [ ] 초대코드 재생성 기능
- [ ] 그룹 탈퇴 API
- [ ] 멤버 강퇴 API (ADMIN)
