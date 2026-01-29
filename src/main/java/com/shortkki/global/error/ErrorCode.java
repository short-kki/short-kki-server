package com.shortkki.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_002", "잘못된 입력 값입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "COMMON_003", "잘못된 타입입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_004", "지원하지 않는 HTTP 메서드입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "COMMON_005", "접근 권한이 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "COMMON_006", "존재하지 않는 리소스 입니다."),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, "COMMON_007", "요청한 작업을 수행할 수 없는 상태입니다."),
    NOT_IMPLEMENTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_008", "아직 구현되지 않은 기능입니다."),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_001", "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "만료된 토큰입니다."),
    OAUTH_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_004", "소셜 로그인 인증에 실패했습니다."),
    PLATFORM_REQUIRED_FOR_GOOGLE(HttpStatus.BAD_REQUEST, "AUTH_005",
            "Google 로그인 시 platform은 필수입니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "회원을 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER_002", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_003", "비밀번호가 일치하지 않습니다."),

    // Recipe
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "RECIPE_001", "레시피를 찾을 수 없습니다."),
    INGREDIENT_REQUIRED(HttpStatus.BAD_REQUEST, "RECIPE_002", "재료는 최소 1개 이상이어야 합니다."),
    STEP_REQUIRED(HttpStatus.BAD_REQUEST, "RECIPE_003", "조리 순서는 최소 1개 이상이어야 합니다."),
    IMPORTED_RECIPE_NOT_MODIFIABLE(HttpStatus.FORBIDDEN, "RECIPE_004",
            "외부에서 가져온 레시피는 수정하거나 삭제할 수 없습니다."),

    // Group
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "GROUP_001", "그룹을 찾을 수 없습니다."),
    GROUP_ACCESS_DENIED(HttpStatus.FORBIDDEN, "GROUP_002", "그룹에 대한 접근 권한이 없습니다."),
    GROUP_ADMIN_REQUIRED(HttpStatus.FORBIDDEN, "GROUP_003", "그룹 관리자 권한이 필요합니다."),
    GROUP_ALREADY_JOINED(HttpStatus.CONFLICT, "GROUP_004", "이미 가입된 그룹입니다."),
    GROUP_INVALID_INVITE_CODE(HttpStatus.BAD_REQUEST, "GROUP_005", "유효하지 않은 초대 코드입니다."),
    GROUP_NOT_MEMBER(HttpStatus.FORBIDDEN, "GROUP_006", "그룹 멤버가 아닙니다."),
    GROUP_NAME_EMPTY(HttpStatus.BAD_REQUEST, "GROUP_007", "그룹 이름은 비어있을 수 없습니다."),
    GROUP_INVITE_CODE_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "GROUP_008", "초대 코드 생성에 실패했습니다."),
    GROUP_INVITE_LINK_EXPIRED(HttpStatus.BAD_REQUEST, "GROUP_009", "만료된 초대 링크입니다."),
    GROUP_CANNOT_KICK_SELF(HttpStatus.BAD_REQUEST, "GROUP_010", "본인을 강퇴할 수 없습니다."),
    GROUP_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "GROUP_011", "그룹 멤버를 찾을 수 없습니다."),

    // Ingredient
    INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "INGREDIENT_001", "재료를 찾을 수 없습니다."),

    // ShoppingList
    SHOPPING_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "SHOPPING_LIST_001", "장볼거리를 찾을 수 없습니다."),
    SHOPPING_LIST_NOT_IN_GROUP(HttpStatus.BAD_REQUEST, "SHOPPING_LIST_002", "해당 그룹의 장볼거리가 아닙니다."),
    // Source
    UNSUPPORTED_SOURCE_PLATFORM(HttpStatus.BAD_REQUEST, "SOURCE_001", "지원하지 않는 출처 플랫폼입니다."),
    SOURCE_URL_REQUIRED(HttpStatus.BAD_REQUEST, "SOURCE_002", "출처 URL은 필수입니다."),
    SOURCE_CONTENT_ALREADY_EXISTS(HttpStatus.CONFLICT, "SOURCE_003", "이미 등록된 외부 컨텐츠입니다."),
    SOURCE_URL_PLATFORM_MISMATCH(HttpStatus.BAD_REQUEST, "SOURCE_004", "URL이 지정된 플랫폼과 일치하지 않습니다."),
    SOURCE_INFO_REQUIRED_FOR_IMPORTED(HttpStatus.BAD_REQUEST, "SOURCE_005", "외부 레시피는 출처 정보가 필수입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
