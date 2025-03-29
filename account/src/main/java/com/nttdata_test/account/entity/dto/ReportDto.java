package com.nttdata_test.account.entity.dto;

import com.nttdata_test.account.entity.enums.AccountType;
import java.time.LocalDate;

public record ReportDto(
    LocalDate date,
    String client,
    String accountNumber,
    AccountType type,
    Double initialBalance,
    Boolean status,
    Double movement,
    Double availableFunds) {}
