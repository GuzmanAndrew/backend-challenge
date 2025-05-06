package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseException {
  public ExternalServiceException(ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, HttpStatus.SERVICE_UNAVAILABLE, cause);
  }

  public ExternalServiceException(ErrorMessage errorMessage) {
    super(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
  }
}
