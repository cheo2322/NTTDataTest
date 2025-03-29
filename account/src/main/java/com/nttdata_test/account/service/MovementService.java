package com.nttdata_test.account.service;

import com.nttdata_test.account.entity.dto.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** CRUD service for Movement table. */
public interface MovementService {

  /**
   * Create a new Movement.
   *
   * @param movementDto with information for the new Movement.
   * @return a Mono Void.
   */
  Mono<Void> createMovement(MovementDto movementDto);

  /**
   * Get one Movement by ID.
   *
   * @param id to get the Movement.
   * @return the Movement.
   */
  Mono<MovementDto> getMovementById(Long id);

  /**
   * Get Movements by Account.
   *
   * @param accountId to get the Movements.
   * @return a Flux with the Movements.
   */
  Flux<MovementDto> getMovementsByAccountId(Long accountId);
}
