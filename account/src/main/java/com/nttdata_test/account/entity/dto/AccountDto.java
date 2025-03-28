package com.nttdata_test.account.entity.dto;

public record AccountDto(
    String accountNumber,
    String accountType,
    Double initialBalance,
    Boolean status,
    Long clientId) {}
