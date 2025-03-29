package com.nttdata_test.account.controller;

import com.nttdata_test.account.entity.dto.MovementDto;
import com.nttdata_test.account.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/nttdata/test/v1/movements")
public class MovementController {

  private final MovementService movementService;

  public MovementController(MovementService movementService) {
    this.movementService = movementService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createMovement(@RequestBody MovementDto movementDto) {
    return movementService.createMovement(movementDto);
  }

  @GetMapping("/{id}")
  public Mono<MovementDto> getMovementById(@PathVariable Long id) {
    return movementService.getMovementById(id);
  }

  @GetMapping("/{accountNumber}")
  public Flux<MovementDto> getMovementsByAccountId(@PathVariable Long accountId) {
    return movementService.getMovementsByAccountId(accountId);
  }
}
