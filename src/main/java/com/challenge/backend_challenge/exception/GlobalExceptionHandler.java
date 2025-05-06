package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;
import com.challenge.backend_challenge.model.dto.ErrorResponse;
import com.challenge.backend_challenge.model.dto.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
    ErrorResponse error =
        new ErrorResponse(
            ex.getStatus().value(), ex.getErrorCode(), ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(error, ex.getStatus());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParams(
      MissingServletRequestParameterException ex) {
    ErrorResponse error =
        new ErrorResponse(
            400,
            ErrorMessage.VALIDATION_ERROR.name(),
            "El parámetro '" + ex.getParameterName() + "' es obligatorio",
            LocalDateTime.now());
    return new ResponseEntity<>(error, org.springframework.http.HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ValidationErrorResponse response =
        new ValidationErrorResponse(
            400,
            ErrorMessage.VALIDATION_ERROR.name(),
            "Errores de validación en los campos",
            LocalDateTime.now(),
            errors);

    return new ResponseEntity<>(response, org.springframework.http.HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(
            500, ErrorMessage.GENERAL_ERROR.name(), ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(error, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
