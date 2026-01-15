# Group 기능 분석 및 구현 현황

## 요구사항 (GROUP-FEAT.md) vs 현재 구현 상태

### 1. 그룹 생성 시 필요 정보

| 항목 | 요구사항 | 현재 상태 | 비고 |
|------|----------|-----------|------|
| 그룹 이름 | 필수 | ✅ 구현됨 | `name` |
| 단위 (그룹 타입) | 필수 (커플/가족/친구/기타) | ⚠️ Entity만 추가됨 | `GroupType` enum 존재, CRUD 미반영 |
| 설명 | 선택 | ✅ 구현됨 | `description` |
| 사진 | 선택 | ✅ 구현됨 | `thumbnailImgUrl` |

### 2. 그룹 기능

| 기능 | 요구사항 | 현재 상태 | 우선순위 |
|------|----------|-----------|----------|
| 그룹 생성 | ADMIN | ✅ 구현됨 | - |
| 그룹 수정 | ADMIN | ✅ 구현됨 | - |
| 그룹 삭제 | ADMIN | ✅ 구현됨 | - |
| 그룹 상세 조회 | 멤버 | ✅ 구현됨 | - |
| 내 그룹 목록 | 인증 | ✅ 구현됨 | - |
| 멤버 목록 조회 | 멤버 | ✅ 구현됨 | - |
| 초대코드 조회 | 멤버 | ✅ 구현됨 | - |
| 그룹 참여 (초대코드) | 인증 | ✅ 구현됨 | - |
| **그룹 탈퇴** | 멤버 | ❌ 미구현 | 높음 |
| **멤버 강퇴** | ADMIN | ❌ 미구현 | 높음 |

### 3. 피드 기능

| 기능 | 요구사항 | 현재 상태 | 우선순위 |
|------|----------|-----------|----------|
| 피드 목록 조회 | 멤버 | ✅ 구현됨 | - |
| 사용자 피드 생성 | 멤버 | ✅ 구현됨 | - |
| **피드 타입 구분** | 사용자생성/식단추가/컬렉션추가 | ❌ 미구현 | 중간 |
| **피드 삭제** | 사용자 생성 피드만 삭제 가능 | ❌ 미구현 | 중간 |
| **피드 알림 여부** | 타입별 알림 O/X 구분 | ❌ 미구현 | 낮음 |

### 4. 추가 도메인 (별도 구현 필요)

| 도메인 | 설명 | 현재 상태 |
|--------|------|-----------|
| 장볼거리 (ShoppingList) | 그룹별 장볼거리 목록 | ❌ TODO 상태 |
| 요리일정 (MealSchedule) | 그룹별 요리 일정 관리 | ❌ 미구현 |
| 컬렉션 (Collection) | 그룹별 레시피 컬렉션 | ❌ 미구현 |

---

## 즉시 수정 필요 사항

### 1. GroupType CRUD 반영
- [x] `GroupType` enum 생성 (COUPLE, FAMILY, FRIENDS, ETC)
- [ ] `CreateGroupRequest`에 `groupType` 추가
- [ ] `UpdateGroupRequest`에 `groupType` 추가
- [ ] `GroupResponse`에 `groupType` 추가
- [ ] `GroupListResponse`에 `groupType` 추가
- [ ] `Group.create()` 메서드에 `groupType` 파라미터 추가
- [ ] `Group.updateGroupInfo()` 메서드에 `groupType` 파라미터 추가
- [ ] `GroupService` 수정

---

## 추가 구현 필요 API

### 그룹 탈퇴
```
DELETE /api/v1/groups/{groupId}/leave
- 권한: 멤버
- 로직: MemberGroup 삭제
- 제약: ADMIN은 탈퇴 불가 (그룹 삭제 또는 권한 위임 필요)
```

### 멤버 강퇴
```
DELETE /api/v1/groups/{groupId}/members/{memberId}
- 권한: ADMIN
- 로직: 해당 멤버의 MemberGroup 삭제
- 제약: 자기 자신 강퇴 불가
```

### 피드 삭제
```
DELETE /api/v1/groups/{groupId}/feeds/{feedId}
- 권한: 피드 작성자 본인
- 제약: 사용자 생성 피드(USER_CREATED)만 삭제 가능
```

---

## Feed 도메인 확장 (향후)

### FeedType enum 추가
```java
public enum FeedType {
    USER_CREATED,      // 사용자 생성 피드 (알림 O, 삭제 O)
    MEAL_ADDED,        // 식단 추가 피드 (알림 O, 삭제 X)
    COLLECTION_ADDED   // 컬렉션 추가 피드 (알림 X, 삭제 X)
}
```

### Feed 엔티티 확장
- `feedType` 필드 추가
- `deletable` 여부 판단 메서드 추가
- `notifiable` 여부 판단 메서드 추가
