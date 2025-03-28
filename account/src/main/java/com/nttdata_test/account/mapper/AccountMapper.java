package com.nttdata_test.account.mapper;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.dto.AccountDto;
import com.nttdata_test.account.entity.enums.AccountType;

public class AccountMapper {

  public static Account dtoToAccount(AccountDto accountDto) {
    return Account.builder()
        .accountNumber(accountDto.accountNumber())
        .accountType(AccountType.valueOf(accountDto.accountType()))
        .initialBalance(accountDto.initialBalance())
        .status(accountDto.status())
        .clientId(accountDto.clientId())
        .build();
  }

  public static AccountDto accountToDto(Account account) {
    return new AccountDto(
        account.getAccountNumber(),
        account.getAccountType().name(),
        account.getInitialBalance(),
        account.getStatus(),
        account.getClientId());
  }
}
