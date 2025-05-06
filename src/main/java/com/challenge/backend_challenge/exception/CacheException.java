package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class CacheException extends BaseException {
  public CacheException(ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }

  public CacheException(ErrorMessage errorMessage) {
    super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
