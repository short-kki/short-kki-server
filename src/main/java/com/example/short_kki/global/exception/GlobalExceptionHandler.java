package com.example.short_kki.global.exception;

import com.example.short_kki.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<BaseResponse<Void>> handleBusinessException(BusinessException e) {
    log.error("BusinessException: {}", e.getMessage());
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(BaseResponse.error(errorCode.getCode(), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String errorMessage = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    log.error("MethodArgumentNotValidException: {}", errorMessage);
    return ResponseEntity
        .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
  }

  @ExceptionHandler(BindException.class)
  protected ResponseEntity<BaseResponse<Void>> handleBindException(BindException e) {
    String errorMessage = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    log.error("BindException: {}", errorMessage);
    return ResponseEntity
        .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.error("MethodArgumentTypeMismatchException: {}", e.getMessage());
    return ResponseEntity
        .status(ErrorCode.INVALID_TYPE_VALUE.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.INVALID_TYPE_VALUE.getCode(),
            ErrorCode.INVALID_TYPE_VALUE.getMessage()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<BaseResponse<Void>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());
    return ResponseEntity
        .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.METHOD_NOT_ALLOWED.getCode(),
            ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<BaseResponse<Void>> handleAccessDeniedException(
      AccessDeniedException e) {
    log.error("AccessDeniedException: {}", e.getMessage());
    return ResponseEntity
        .status(ErrorCode.ACCESS_DENIED.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.ACCESS_DENIED.getCode(),
            ErrorCode.ACCESS_DENIED.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<BaseResponse<Void>> handleRuntimeException(RuntimeException e) {
    log.error("RuntimeException: ", e);
    return ResponseEntity
        .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
    log.error("Exception: ", e);
    return ResponseEntity
        .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }
}
