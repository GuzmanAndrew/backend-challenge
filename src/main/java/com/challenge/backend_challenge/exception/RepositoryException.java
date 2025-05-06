package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class RepositoryException extends BaseException {
  public RepositoryException(ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }

  public RepositoryException(ErrorMessage errorMessage) {
    super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
