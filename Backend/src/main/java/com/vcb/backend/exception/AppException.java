package com.vcb.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException{
  private final AppError appError;

  public AppException(AppError appError) {
    super(appError.getMessage());
    this.appError = appError;
  }
}
