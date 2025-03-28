package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.dto.AccountDto;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Mono<Void> createAccount(AccountDto accountDto) {
    return null;
  }

  @Override
  public Mono<AccountDto> getAccount(String accountNumber) {
    return null;
  }

  @Override
  public Mono<Void> updateAccount(String accountNumber, AccountDto accountDto) {
    return null;
  }

  @Override
  public Mono<Void> deleteAccount(String accountNumber) {
    return null;
  }
}
