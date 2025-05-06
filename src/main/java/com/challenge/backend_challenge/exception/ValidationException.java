package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ValidationException extends BaseException {
  public ValidationException(ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, HttpStatus.BAD_REQUEST, cause);
  }

  public ValidationException(ErrorMessage errorMessage) {
    super(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
