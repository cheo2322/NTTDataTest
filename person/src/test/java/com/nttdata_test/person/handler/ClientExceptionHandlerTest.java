package com.nttdata_test.person.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nttdata_test.person.handler.ex.DuplicateEntityException;
import com.nttdata_test.person.handler.ex.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClientExceptionHandlerTest {

  private final ClientExceptionHandler clientExceptionHandler = new ClientExceptionHandler();

  @Test
  void handleDuplicateEntityException_ShouldReturnBadRequest() {
    // given
    DuplicateEntityException exception = new DuplicateEntityException("Client already exists.");

    // when
    Mono<ResponseEntity<ClientError>> response =
        clientExceptionHandler.handleDuplicateEntityException(exception);

    // then
    StepVerifier.create(response)
        .assertNext(
            entity -> {
              assertEquals(400, entity.getStatusCode().value());
              assertNotNull(entity.getBody());
              assertEquals(400, entity.getBody().status());
              assertEquals("Client already exists.", entity.getBody().message());
            })
        .verifyComplete();
  }

  @Test
  void handleEntityNotFoundException_ShouldReturnNotFound() {
    // given
    EntityNotFoundException exception = new EntityNotFoundException("Client not found.");

    // when
    Mono<ResponseEntity<ClientError>> response =
        clientExceptionHandler.handleEntityNotFoundException(exception);

    // then
    StepVerifier.create(response)
        .assertNext(
            entity -> {
              assertEquals(404, entity.getStatusCode().value());
              assertNotNull(entity.getBody());
              assertEquals(404, entity.getBody().status());
              assertEquals("Client not found.", entity.getBody().message());
            })
        .verifyComplete();
  }

  @Test
  void handleGeneralException_ShouldReturnInternalServerError() {
    // given
    Exception exception = new Exception("Unexpected error occurred.");

    // when
    Mono<ResponseEntity<ClientError>> response =
        clientExceptionHandler.handleGeneralException(exception);

    // then
    StepVerifier.create(response)
        .assertNext(
            entity -> {
              assertEquals(500, entity.getStatusCode().value());
              assertNotNull(entity.getBody());
              assertEquals(500, entity.getBody().status());
              assertEquals("Unexpected error. Please contact support.", entity.getBody().message());
            })
        .verifyComplete();
  }
}
