# Group, Feed, Shopping List & File API 명세서

## 공통 정보

**Base URL**: `http://localhost:8080`

**인증**: `Authorization: Bearer {accessToken}`

**공통 응답 형식**:
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": { ... }
}
```

---

## Enum 타입

### GroupType
| 값 | 설명 |
|----|------|
| `COUPLE` | 커플 |
| `FAMILY` | 가족 |
| `FRIENDS` | 친구 |
| `ETC` | 기타 |

### GroupRole
| 값 | 설명 |
|----|------|
| `ADMIN` | 관리자 |
| `MEMBER` | 일반 멤버 |

### FeedType
| 값 | 설명 |
|----|------|
| `USER_CREATED` | 사용자 생성 피드 |
| `DAILY_MENU_NOTIFICATION` | 오늘의 식단 알림 |
| `NEW_RECIPE_ADDED` | 새 레시피 추가 알림 |

---

## Group API

### 1. 그룹 생성
```
POST /api/v1/groups
```

**인증**: 필수

**Request Body**:
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | String | O | 그룹 이름 (최대 50자) |
| description | String | X | 그룹 설명 (최대 500자) |
| thumbnailImgUrl | String | X | 썸네일 이미지 URL |
| groupType | GroupType | O | 그룹 타입 (COUPLE, FAMILY, FRIENDS, ETC) |

```json
{
  "name": "우리 가족",
  "description": "가족 레시피 공유",
  "thumbnailImgUrl": "https://...",
  "groupType": "FAMILY"
}
```

**Response** (201 Created):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "id": 1,
    "name": "우리 가족",
    "description": "가족 레시피 공유",
    "thumbnailImgUrl": "https://...",
    "groupType": "FAMILY",
    "memberCount": 1,
    "createdAt": "2025-01-15T10:30:00"
  }
}
```

---

### 2. 그룹 수정
```
PUT /api/v1/groups/{groupId}
```

**인증**: 필수 (ADMIN 권한)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Request Body**:
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | String | O | 그룹 이름 (최대 50자) |
| description | String | X | 그룹 설명 (최대 500자) |
| thumbnailImgUrl | String | X | 썸네일 이미지 URL |
| groupType | GroupType | O | 그룹 타입 |

```json
{
  "name": "우리 가족 (수정)",
  "description": "가족 레시피 공유 그룹",
  "thumbnailImgUrl": "https://...",
  "groupType": "FAMILY"
}
```

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

---

### 3. 그룹 삭제
```
DELETE /api/v1/groups/{groupId}
```

**인증**: 필수 (ADMIN 권한)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 그룹 삭제 시 관련 피드, 장볼거리, 초대 링크, 멤버 정보가 모두 삭제됩니다.

---

### 4. 그룹 상세 조회
```
GET /api/v1/groups/{groupId}
```

**인증**: 필수

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "id": 1,
    "name": "우리 가족",
    "description": "가족 레시피 공유",
    "thumbnailImgUrl": "https://...",
    "groupType": "FAMILY",
    "memberCount": 3,
    "createdAt": "2025-01-15T10:30:00"
  }
}
```

---

### 5. 내 그룹 목록 조회
```
GET /api/v1/groups/my
```

**인증**: 필수

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": [
    {
      "id": 1,
      "name": "우리 가족",
      "description": "가족 레시피 공유",
      "thumbnailImgUrl": "https://...",
      "groupType": "FAMILY",
      "myRole": "ADMIN",
      "createdAt": "2025-01-15T10:30:00"
    },
    {
      "id": 2,
      "name": "친구들",
      "description": "친구들과 레시피 공유",
      "thumbnailImgUrl": null,
      "groupType": "FRIENDS",
      "myRole": "MEMBER",
      "createdAt": "2025-01-20T14:00:00"
    }
  ]
}
```

---

### 6. 그룹 멤버 조회
```
GET /api/v1/groups/{groupId}/members
```

**인증**: 필수

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": [
    {
      "memberId": 1,
      "name": "홍길동",
      "email": "hong@example.com",
      "role": "ADMIN",
      "joinedAt": "2025-01-15T10:30:00"
    },
    {
      "memberId": 2,
      "name": "김철수",
      "email": "kim@example.com",
      "role": "MEMBER",
      "joinedAt": "2025-01-16T09:00:00"
    }
  ]
}
```

---

### 7. 초대 코드로 그룹 미리보기
```
GET /api/v1/groups/invite/{inviteCode}
```

**인증**: 불필요

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| inviteCode | String | 초대 코드 |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "id": 1,
    "name": "우리 가족",
    "description": "가족 레시피 공유",
    "thumbnailImgUrl": "https://...",
    "groupType": "FAMILY",
    "memberCount": 3
  }
}
```

---

### 8. 초대 코드로 그룹 가입
```
POST /api/v1/groups/invite/{inviteCode}/join
```

**인증**: 필수

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| inviteCode | String | 초대 코드 |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "id": 1,
    "name": "우리 가족",
    "description": "가족 레시피 공유",
    "thumbnailImgUrl": "https://...",
    "groupType": "FAMILY",
    "memberCount": 4,
    "createdAt": "2025-01-15T10:30:00"
  }
}
```

---

### 9. 초대 코드 조회/생성
```
GET /api/v1/groups/{groupId}/invites
```

**인증**: 필수

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "inviteCode": "ABC12345",
    "expiresAt": "2025-01-22T10:30:00"
  }
}
```

> 유효한 초대 코드가 없으면 새로 생성하여 반환합니다.

---

### 10. 멤버 강퇴
```
DELETE /api/v1/groups/{groupId}/members/{memberId}
```

**인증**: 필수 (ADMIN 권한)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |
| memberId | Long | 강퇴할 멤버 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 본인을 강퇴할 수 없습니다.

---

## Feed API

### 1. 그룹 피드 조회
```
GET /api/v1/groups/{groupId}/feeds
```

**인증**: 필수 (그룹 멤버만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": [
    {
      "id": 1,
      "content": "오늘 저녁은 김치찌개!",
      "feedType": "USER_CREATED",
      "authorId": 1,
      "authorName": "홍길동",
      "likes": 5,
      "likedByMe": true,
      "createdAt": "2025-01-15T18:30:00"
    },
    {
      "id": 2,
      "content": "새 레시피가 추가되었습니다: 된장찌개",
      "feedType": "NEW_RECIPE_ADDED",
      "authorId": 2,
      "authorName": "김철수",
      "likes": 2,
      "likedByMe": false,
      "createdAt": "2025-01-16T12:00:00"
    }
  ]
}
```

**Response 필드**:
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 피드 ID |
| content | String | 피드 내용 |
| feedType | FeedType | 피드 타입 |
| authorId | Long | 작성자 ID |
| authorName | String | 작성자 이름 |
| likes | Long | 좋아요 수 |
| likedByMe | boolean | 내가 좋아요 했는지 여부 |
| createdAt | LocalDateTime | 작성일시 |

---

### 2. 피드 생성
```
POST /api/v1/groups/{groupId}/feeds
```

**인증**: 필수 (그룹 멤버만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |

**Request Body**:
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| content | String | O | 피드 내용 (최대 2000자) |
| feedType | FeedType | O | 피드 타입 (USER_CREATED, DAILY_MENU_NOTIFICATION, NEW_RECIPE_ADDED) |

```json
{
  "content": "오늘 저녁은 김치찌개!",
  "feedType": "USER_CREATED"
}
```

**Response** (201 Created):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

---

### 3. 피드 삭제
```
DELETE /api/v1/groups/{groupId}/feeds/{feedId}
```

**인증**: 필수 (작성자 본인만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |
| feedId | Long | 피드 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 피드 삭제 시 관련 좋아요 기록도 함께 삭제됩니다.

---

### 4. 피드 좋아요
```
POST /api/v1/groups/{groupId}/feeds/{feedId}/like
```

**인증**: 필수 (그룹 멤버만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |
| feedId | Long | 피드 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 이미 좋아요한 피드에 다시 좋아요를 누르면 `FEED_001` 에러가 발생합니다.

---

### 5. 피드 좋아요 취소
```
DELETE /api/v1/groups/{groupId}/feeds/{feedId}/like
```

**인증**: 필수 (그룹 멤버만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| groupId | Long | 그룹 ID |
| feedId | Long | 피드 ID |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 좋아요하지 않은 피드의 좋아요를 취소하면 `FEED_002` 에러가 발생합니다.

---

## Shopping List API

### 1. 장볼거리 목록 조회
```
GET /api/v1/groups/{groupId}/shopping-list
```

**인증**: 필수

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": [
    {
      "id": 1,
      "name": "양파",
      "ingredientId": 10,
      "createdAt": "2025-01-15T10:30:00"
    }
  ]
}
```

---

### 2. 장볼거리 일괄 생성
```
POST /api/v1/groups/{groupId}/shopping-list/bulk
```

**인증**: 필수

**Request Body**:
```json
{
  "items": [
    { "recipeIngredientId": 1 },
    { "recipeIngredientId": 2 }
  ]
}
```

**Response** (201 Created):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

---

### 3. 장볼거리 삭제
```
DELETE /api/v1/groups/{groupId}/shopping-list/{shoppingListId}
```

**인증**: 필수

---

## File API

> Presigned URL 기반 파일 업로드 API. 클라이언트는 먼저 업로드 URL을 요청한 후, 해당 URL로 직접 파일을 업로드합니다.

### Enum 타입

#### FileTargetType
| 값 | 설명 | 허용 Content-Type |
|----|------|-------------------|
| `MEMBER_PROFILE_IMG` | 회원 프로필 이미지 | image/jpeg, image/png, image/webp, image/heic, image/heif |
| `RECIPE_IMG` | 레시피 이미지 | image/jpeg, image/png, image/webp, image/heic, image/heif |

#### FileVisibility
| 값 | 설명 |
|----|------|
| `PUBLIC` | 공개 파일 |
| `PRIVATE` | 비공개 파일 |

#### UploadStatus
| 값 | 설명 |
|----|------|
| `PENDING` | 업로드 대기 중 |
| `UPLOADED` | 업로드 완료 |
| `FAILED` | 업로드 실패 |
| `DELETED` | 삭제됨 |

---

### 1. 업로드 URL 요청
```
POST /api/v1/files/uploads
```

**인증**: 필수

**Request Body**:
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| filename | String | O | 파일명 |
| contentType | String | O | 파일 MIME 타입 (예: image/jpeg) |
| contentLength | long | O | 파일 크기 (bytes, 0보다 커야 함) |
| visibility | FileVisibility | O | 파일 공개 여부 (PUBLIC, PRIVATE) |
| targetType | FileTargetType | O | 파일 용도 (MEMBER_PROFILE_IMG, RECIPE_IMG) |

```json
{
  "filename": "profile.jpg",
  "contentType": "image/jpeg",
  "contentLength": 102400,
  "visibility": "PUBLIC",
  "targetType": "MEMBER_PROFILE_IMG"
}
```

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": {
    "fileId": 1,
    "objectKey": "member/profile/abc123-uuid.jpg",
    "uploadUrl": "https://s3.amazonaws.com/bucket/...",
    "method": "PUT",
    "headers": {
      "Content-Type": "image/jpeg"
    },
    "expiresAt": "2025-01-15T10:45:00Z"
  }
}
```

**Response 필드**:
| 필드 | 타입 | 설명 |
|------|------|------|
| fileId | Long | 파일 메타데이터 ID (업로드 완료 시 사용) |
| objectKey | String | S3 객체 키 |
| uploadUrl | String | Presigned 업로드 URL |
| method | String | HTTP 메서드 (PUT) |
| headers | Map | 업로드 시 포함해야 할 헤더 |
| expiresAt | Instant | URL 만료 시간 |

**업로드 흐름**:
1. 이 API로 uploadUrl 획득
2. uploadUrl로 파일 직접 PUT 요청 (headers 포함)
3. 업로드 완료 후 markUploaded API 호출

---

### 2. 업로드 완료 처리
```
PATCH /api/v1/files/uploads/{fileId}
```

**인증**: 필수 (업로드 요청자 본인만)

**Path Parameters**:
| 파라미터 | 타입 | 설명 |
|---------|------|------|
| fileId | Long | 파일 ID (업로드 URL 요청 시 받은 fileId) |

**Response** (200 OK):
```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": null
}
```

> 파일 상태가 PENDING → UPLOADED로 변경됩니다.

---

## 에러 코드

| 코드 | HTTP 상태 | 설명 |
|------|----------|------|
| GROUP_001 | 404 | 그룹을 찾을 수 없습니다 |
| GROUP_002 | 403 | 그룹에 대한 접근 권한이 없습니다 |
| GROUP_003 | 403 | 그룹 관리자 권한이 필요합니다 |
| GROUP_004 | 409 | 이미 가입된 그룹입니다 |
| GROUP_005 | 400 | 유효하지 않은 초대 코드입니다 |
| GROUP_006 | 403 | 그룹 멤버가 아닙니다 |
| GROUP_009 | 400 | 만료된 초대 링크입니다 |
| GROUP_010 | 400 | 본인을 강퇴할 수 없습니다 |
| GROUP_011 | 404 | 그룹 멤버를 찾을 수 없습니다 |
| SHOPPING_LIST_001 | 404 | 장볼거리를 찾을 수 없습니다 |
| SHOPPING_LIST_002 | 400 | 해당 그룹의 장볼거리가 아닙니다 |
| FEED_001 | 409 | 이미 좋아요한 피드입니다 |
| FEED_002 | 404 | 좋아요 기록을 찾을 수 없습니다 |
| COMMON_002 | 400 | 잘못된 입력 값입니다 (파일 관련: visibility/contentType 누락, contentLength 범위 초과, 지원하지 않는 확장자) |
| COMMON_005 | 403 | 접근 권한이 없습니다 (파일 업로더와 요청자 불일치) |
