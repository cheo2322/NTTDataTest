package com.nttdata_test.account.service;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.dto.AccountDto;
import reactor.core.publisher.Mono;

/** CRUD operations for Account document. */
public interface AccountService {

  /**
   * Create an {@link Account} document.
   *
   * @param accountDto with the information for the Account.
   * @return a Mono Void.
   */
  Mono<Void> createAccount(AccountDto accountDto);

  /**
   * Get an {@link Account} by accountNumber.
   *
   * @param accountNumber to get the Account.
   * @return an AccountDto with the information from the Account.
   */
  Mono<AccountDto> getAccount(String accountNumber);

  /**
   * Update an {@link Account} document.
   *
   * @param accountNumber to find the Account.
   * @param accountDto with the information for the Account.
   * @return a Mono Void.
   */
  Mono<Void> updateAccount(String accountNumber, AccountDto accountDto);

  /**
   * Delete an {@link Account} by changing its status.
   *
   * @param accountNumber to find the Account.
   * @return a Mono Void.
   */
  Mono<Void> deleteAccount(String accountNumber);
}
