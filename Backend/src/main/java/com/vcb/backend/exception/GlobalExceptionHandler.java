package com.vcb.backend.exception;

import com.vcb.backend.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  private static final String MIN_ATTRIBUTE = "min";

  String exLog(Exception ex) {
    String classname = ex.getStackTrace()[0].getClassName();
    String methodName = ex.getStackTrace()[0].getMethodName();
    int lineNumber = ex.getStackTrace()[0].getLineNumber();
    String message = ex.getMessage();

    return ("Exception in class: " + classname + ", method: " + methodName + ", line: " + lineNumber + ", message: " + message);
  }

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    AppError error = AppError.UNCATEGORIZED_EXCEPTION;

    log.error(exLog(e));

    return ResponseEntity
        .status(error.getStatusCode())
        .body(ApiResponse.<Void>builder()
          .code(error.getCode())
          .message(error.getMessage() + ": " + e.getMessage())
          .build());
  }


  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<Void>> handleApplicationException(AppException e) {

    log.error(exLog(e));

    return ResponseEntity
        .status(e.getAppError().getStatusCode())
      .body(ApiResponse.<Void>builder()
        .code(e.getAppError().getCode())
        .message(e.getAppError().getMessage())
        .build());
  }

  private String mapAttribute(String message, Map<String, Object> attributes) {
    String minValue = attributes.get(MIN_ATTRIBUTE).toString();

    return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<Void>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error(exLog(e));

    String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();

    AppError errorCode;

    ApiResponse<Void> apiResponse = new ApiResponse<>();

    try {
      errorCode = AppError.valueOf(enumKey);

      apiResponse.setCode(errorCode.getCode());

      if(errorCode.getMessage().contains("{min}")){
        Map<String, Object> attributes;

        var constraintViolations = e.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

        attributes = constraintViolations.getConstraintDescriptor().getAttributes();

        log.info("Attributes: {}", attributes.toString());

        apiResponse.setMessage(mapAttribute(errorCode.getMessage(), attributes));
      }else{
        apiResponse.setMessage(errorCode.getMessage());
      }
    }catch (IllegalArgumentException ex){
      apiResponse.setCode(AppError.UNCATEGORIZED_EXCEPTION.getCode());
      apiResponse.setMessage(enumKey);
    }

    return ResponseEntity.badRequest().body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException e) {
    AppError appError = AppError.UNAUTHENTICATED;
    log.error(exLog(e));
    return ResponseEntity.status(appError.getStatusCode()).body(ApiResponse.builder()
      .code(appError.getCode()).message(appError.getMessage())
      .build());
  }

}
