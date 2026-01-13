package com.example.short_kki.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

  private final String code;
  private final String message;
  private final T data;

  public static <T> BaseResponse<T> success(T data) {
    return BaseResponse.<T>builder()
        .code("SUCCESS")
        .message("요청이 성공적으로 처리되었습니다.")
        .data(data)
        .build();
  }

  public static <T> BaseResponse<T> success(String message, T data) {
    return BaseResponse.<T>builder()
        .code("SUCCESS")
        .message(message)
        .data(data)
        .build();
  }

  public static BaseResponse<Void> success() {
    return BaseResponse.<Void>builder()
        .code("SUCCESS")
        .message("요청이 성공적으로 처리되었습니다.")
        .build();
  }

  public static BaseResponse<Void> success(String message) {
    return BaseResponse.<Void>builder()
        .code("SUCCESS")
        .message(message)
        .build();
  }

  public static <T> BaseResponse<T> error(String code, String message) {
    return BaseResponse.<T>builder()
        .code(code)
        .message(message)
        .build();
  }

  public static <T> BaseResponse<T> error(String code, String message, T data) {
    return BaseResponse.<T>builder()
        .code(code)
        .message(message)
        .data(data)
        .build();
  }
}
