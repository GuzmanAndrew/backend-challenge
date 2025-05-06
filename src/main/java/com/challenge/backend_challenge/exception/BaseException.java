package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {

  private final HttpStatus status;
  private final String errorCode;

  protected BaseException(ErrorMessage errorMessage, HttpStatus status, Throwable cause) {
    super(errorMessage.getMessage(), cause);
    this.status = status;
    this.errorCode = errorMessage.name();
  }

  protected BaseException(ErrorMessage errorMessage, HttpStatus status) {
    super(errorMessage.getMessage());
    this.status = status;
    this.errorCode = errorMessage.name();
  }

}
