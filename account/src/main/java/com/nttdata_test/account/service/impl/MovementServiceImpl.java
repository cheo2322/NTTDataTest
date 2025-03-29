package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.Movement;
import com.nttdata_test.account.entity.dto.MovementDto;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.handler.ex.NegativeBalanceException;
import com.nttdata_test.account.mapper.MovementMapper;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.repository.MovementRepository;
import com.nttdata_test.account.service.MovementService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

  public final AccountRepository accountRepository;
  public final MovementRepository movementRepository;

  public MovementServiceImpl(
      AccountRepository accountRepository, MovementRepository movementRepository) {
    this.accountRepository = accountRepository;
    this.movementRepository = movementRepository;
  }

  @Override
  public Mono<Void> createMovement(MovementDto movementDto) {
    return accountRepository
        .findById(movementDto.accountId())
        .filter(Account::getStatus)
        .flatMap(account -> checkLastMovementAndSave(movementDto, account))
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")))
        .then();
  }

  @Override
  public Mono<MovementDto> getMovementById(Long id) {
    return movementRepository
        .findById(id)
        .map(MovementMapper::movementToDto)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")));
  }

  @Override
  public Flux<MovementDto> getMovementsByAccountId(Long accountId) {
    return accountRepository
        .findById(accountId)
        .filter(Account::getStatus)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found .")))
        .flatMapMany(account -> movementRepository.findByAccountId(accountId))
        .map(MovementMapper::movementToDto);
  }

  /**
   * Save the Movement.
   *
   * @param movementDto with the information for the Movement.
   * @param account to add information to the Movement.
   * @return the saved Movement.
   */
  private Mono<Movement> checkLastMovementAndSave(MovementDto movementDto, Account account) {
    return movementRepository
        .findLastMovementByDate()
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Last movement not found.")))
        .flatMap(
            lastMovement -> {
              double newBalance = lastMovement.getBalance() + movementDto.balance();
              return newBalance >= 0
                  ? saveMovement(movementDto, account, newBalance)
                  : Mono.error(new NegativeBalanceException("Not enough funds."));
            });
  }

  private Mono<Movement> saveMovement(MovementDto movementDto, Account account, double newBalance) {
    return movementRepository
        .save(
            Movement.builder()
                .movementDate(LocalDateTime.now())
                .accountType(account.getAccountType())
                .movementValue(movementDto.value())
                .balance(newBalance)
                .accountId(account.getId())
                .build())
        .doOnSuccess(
            movementDB ->
                System.out.printf(
                    "Movement %d created for account %d%n", movementDB.getId(), account.getId()));
  }
}
