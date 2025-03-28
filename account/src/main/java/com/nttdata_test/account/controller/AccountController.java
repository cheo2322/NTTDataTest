package com.nttdata_test.account.controller;

import com.nttdata_test.account.entity.dto.AccountDto;
import com.nttdata_test.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("nttdata/test/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createAccount(@RequestBody AccountDto accountDto) {
    return accountService.createAccount(accountDto);
  }

  @GetMapping("/{accountNumber}")
  public Mono<AccountDto> getAccount(@PathVariable String accountNumber) {
    return accountService.getAccount(accountNumber);
  }

  @PutMapping("/{accountNumber}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> updateAccount(
      @PathVariable String accountNumber, @RequestBody AccountDto accountDto) {
    return accountService.updateAccount(accountNumber, accountDto);
  }

  @DeleteMapping("/{accountNumber}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAccount(@PathVariable String accountNumber) {
    return accountService.deleteAccount(accountNumber);
  }
}
