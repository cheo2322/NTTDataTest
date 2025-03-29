package com.nttdata_test.account.mapper;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.Movement;
import com.nttdata_test.account.entity.dto.ReportDto;

public class ReportMapper {

  public static ReportDto buildReportDto(Account account, Movement movement) {
    return new ReportDto(
        movement.getMovementDate().toLocalDate(),
        account.getClientId(),
        account.getAccountNumber(),
        account.getAccountType(),
        account.getInitialBalance(),
        account.getStatus(),
        movement.getMovementValue(),
        movement.getBalance());
  }
}
