package com.nttdata_test.account.service.impl;

import com.nttdata_test.account.entity.Account;
import com.nttdata_test.account.entity.dto.ReportDto;
import com.nttdata_test.account.entity.response.ClientDto;
import com.nttdata_test.account.handler.ex.EntityNotFoundException;
import com.nttdata_test.account.mapper.ReportMapper;
import com.nttdata_test.account.repository.AccountRepository;
import com.nttdata_test.account.repository.MovementRepository;
import com.nttdata_test.account.service.ReportService;
import com.nttdata_test.account.web.ClientWebClient;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {

  private final AccountRepository accountRepository;
  private final MovementRepository movementRepository;
  private final ClientWebClient clientWebClient;

  public ReportServiceImpl(
      AccountRepository accountRepository,
      MovementRepository movementRepository,
      ClientWebClient clientWebClient) {
    this.accountRepository = accountRepository;
    this.movementRepository = movementRepository;
    this.clientWebClient = clientWebClient;
  }

  @Override
  public Flux<ReportDto> getReport(String clientId, LocalDate start, LocalDate end) {
    return clientWebClient
        .getClient(clientId)
        .filter(ClientDto::status)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Client not found.")))
        .flatMapMany(clientDB -> findAccountsAndMovements(clientId, start, end, clientDB));
  }

  /**
   * Find accounts and movements.
   *
   * @param clientId to find the accounts.
   * @param start range of Movement.
   * @param end range of Movement.
   * @param clientDB to get data for the Report.
   * @return the Report.
   */
  private Flux<ReportDto> findAccountsAndMovements(
      String clientId, LocalDate start, LocalDate end, ClientDto clientDB) {
    return accountRepository
        .findByClientId(clientId)
        .filter(Account::getStatus)
        .flatMap(
            accountDB ->
                movementRepository
                    .findByAccountNumberAndDateRange(
                        accountDB.getAccountNumber(),
                        start.atStartOfDay(),
                        end.atTime(LocalTime.MAX))
                    .map(
                        movementDB ->
                            ReportMapper.buildReportDto(accountDB, movementDB, clientDB)));
  }
}
