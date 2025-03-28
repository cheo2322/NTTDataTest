package com.nttdata_test.person.handler;

import com.nttdata_test.person.handler.ex.DuplicateEntityException;
import com.nttdata_test.person.handler.ex.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ClientExceptionHandler {

  @ExceptionHandler(DuplicateEntityException.class)
  public Mono<ResponseEntity<ClientError>> handleDuplicateEntityException(
      DuplicateEntityException ex) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ClientError(HttpStatus.BAD_REQUEST.value(), ex.getMessage())));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public Mono<ResponseEntity<ClientError>> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ClientError(HttpStatus.NOT_FOUND.value(), ex.getMessage())));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ClientError>> handleGeneralException(Exception ex) {
    System.out.println(ex.getLocalizedMessage());
    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ClientError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error. Please contact support.")));
  }
}
