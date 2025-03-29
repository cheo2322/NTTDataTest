package com.nttdata_test.account.mapper;

import com.nttdata_test.account.entity.Movement;
import com.nttdata_test.account.entity.dto.MovementDto;

public class MovementMapper {

  public static MovementDto movementToDto(Movement movement) {
    return new MovementDto(
        movement.getAccountNumber(), movement.getMovementValue(), movement.getBalance());
  }
}
