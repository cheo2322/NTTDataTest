package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.Movement;
import com.nttdata_test.account.entity.dto.AccountDto;
import com.nttdata_test.account.handler.ex.DuplicateEntityException;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.mapper.AccountMapper;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.repository.MovementRepository;
import com.nttdata_test.account.service.AccountService;
import com.nttdata_test.account.web.ClientWebClient;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final MovementRepository movementRepository;
  private final ClientWebClient clientWebClient;

  public AccountServiceImpl(
      AccountRepository accountRepository,
      MovementRepository movementRepository,
      ClientWebClient clientWebClient) {
    this.accountRepository = accountRepository;
    this.movementRepository = movementRepository;
    this.clientWebClient = clientWebClient;
  }

  @Override
  public Mono<Void> createAccount(AccountDto accountDto) {
    return clientWebClient
        .getClient(accountDto.clientId())
        .flatMap(
            client ->
                accountRepository
                    .save(AccountMapper.dtoToAccount(accountDto))
                    .flatMap(this::saveFirstMovement)
                    .onErrorResume(
                        DuplicateKeyException.class,
                        e ->
                            Mono.error(
                                new DuplicateEntityException("Account number already exists."))))
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
    return accountRepository
        .findByAccountNumber(accountNumber)
        .flatMap(
            account -> {
              if (!account.getStatus()) {
                return Mono.empty();
              }

              if (accountDto.status() != null) {
                account.setStatus(accountDto.status());
              }

              return accountRepository
                  .save(account)
                  .doOnSuccess(
                      updatedAccount ->
                          System.out.println("Updated account: " + updatedAccount.getId()))
                  .then();
            })
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")));
  }

  @Override
  public Mono<Void> deleteAccount(String accountNumber) {
    return accountRepository
        .findByAccountNumber(accountNumber)
        .flatMap(
            account -> {
              if (!account.getStatus()) {
                return Mono.empty();
              }

              account.setStatus(false);

              return accountRepository
                  .save(account)
                  .doOnSuccess(
                      updatedAccount ->
                          System.out.println("Updated account: " + updatedAccount.getId()))
                  .then();
            })
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")));
  }

  /**
   * Save the first Movement when an Account is created.
   *
   * @param account to get the data for the Movement.
   * @return the saved Movement.
   */
  private Mono<Movement> saveFirstMovement(Account account) {
    Movement firstAcountMovement =
        Movement.builder()
            .accountType(account.getAccountType())
            .movementValue(account.getInitialBalance())
            .balance(account.getInitialBalance())
            .accountId(account.getId())
            .movementDate(LocalDateTime.now())
            .build();

    return movementRepository
        .save(firstAcountMovement)
        .doOnSuccess(movement -> System.out.println("First movement created: " + movement.getId()));
  }
}
