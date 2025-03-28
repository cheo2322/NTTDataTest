package com.nttdata_test.account.handler;

import com.nttdata_test.account.handler.ex.DuplicateEntityException;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.handler.ex.ExternalServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class AccountExceptionHandler {

  @ExceptionHandler(DuplicateEntityException.class)
  public Mono<ResponseEntity<AccountError>> handleDuplicateEntityException(
      DuplicateEntityException ex) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new AccountError(HttpStatus.BAD_REQUEST.value(), ex.getMessage())));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public Mono<ResponseEntity<AccountError>> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new AccountError(HttpStatus.NOT_FOUND.value(), ex.getMessage())));
  }

  @ExceptionHandler(ExternalServiceException.class)
  public Mono<ResponseEntity<AccountError>> handleExternalServiceException(
      EntityNotFoundException ex) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new AccountError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<AccountError>> handleGeneralException(Exception ex) {
    System.out.println(ex.getLocalizedMessage());
    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new AccountError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error. Please contact support.")));
  }
}
