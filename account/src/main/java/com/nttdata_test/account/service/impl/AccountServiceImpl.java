package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.dto.AccountDto;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.mapper.AccountMapper;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.service.AccountService;
import com.nttdata_test.account.web.ClientWebClient;
import java.util.Objects;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final ClientWebClient clientWebClient;

  public AccountServiceImpl(AccountRepository accountRepository, ClientWebClient clientWebClient) {
    this.accountRepository = accountRepository;
    this.clientWebClient = clientWebClient;
  }

  @Override
  public Mono<Void> createAccount(AccountDto accountDto) {
    return clientWebClient
        .getClient(accountDto.clientId())
        .flatMap(
            client -> {
              if (client == null || !Objects.equals(client.clientId(), accountDto.clientId())) {
                return Mono.empty();
              }

              return accountRepository
                  .save(AccountMapper.dtoToAccount(accountDto))
                  .onErrorResume(
                      DuplicateKeyException.class,
                      e -> Mono.error(new DuplicateKeyException("Account number already exists.")));
            })
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Client not found.")))
        .then();
  }

  @Override
  public Mono<AccountDto> getAccount(String accountNumber) {
    return accountRepository
        .findByAccountNumber(accountNumber)
        .map(AccountMapper::accountToDto)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")));
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
