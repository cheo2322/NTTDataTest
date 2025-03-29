package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.dto.ReportDto;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.mapper.ReportMapper;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.repository.MovementRepository;
import com.nttdata_test.account.service.ReportService;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {

  private final AccountRepository accountRepository;
  private final MovementRepository movementRepository;

  public ReportServiceImpl(
      AccountRepository accountRepository, MovementRepository movementRepository) {
    this.accountRepository = accountRepository;
    this.movementRepository = movementRepository;
  }

  @Override
  public Flux<ReportDto> getReport(Long id, LocalDate start, LocalDate end) {
    return accountRepository
        .findById(id)
        .filter(Account::getStatus)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found.")))
        .flatMapMany(
            accountDB ->
                movementRepository
                    .findByAccountIdAndDateRange(
                        id, start.atStartOfDay(), end.atTime(LocalTime.MAX))
                    .map(movementDB -> ReportMapper.buildReportDto(accountDB, movementDB)));
  }
}
