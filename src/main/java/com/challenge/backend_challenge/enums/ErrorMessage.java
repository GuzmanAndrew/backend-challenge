package com.challenge.backend_challenge.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
  GENERAL_ERROR("An unexpected error has occurred"),
  EXTERNAL_SERVICE_FAILED("The external service has failed"),
  EXTERNAL_SERVICE_SIMULATION("External service failure simulation"),
  CACHE_UNAVAILABLE("Cache not available"),
  NO_CACHED_VALUE("No value in cache"),
  PERCENTAGE_NOT_FOUND(
      "Could not get the percentage from the external service and there is no cached value"),
  ENTITY_NOT_FOUND("Entity not found"),
  SAVE_ENTITY_ERROR("Error saving the entity"),
  VALIDATION_ERROR("Validation error"),
  INVALID_PAGE_PARAMS("Invalid pagination parameters");

  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

}
