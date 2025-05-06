package com.challenge.backend_challenge.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {

  private Map<String, String> fieldErrors;

  public ValidationErrorResponse(
      int status,
      String errorCode,
      String message,
      LocalDateTime timestamp,
      Map<String, String> fieldErrors) {
    super(status, errorCode, message, timestamp);
    this.fieldErrors = fieldErrors;
  }

}
