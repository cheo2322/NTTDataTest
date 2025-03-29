package com.nttdata_test.account.service;

import com.nttdata_test.account.entity.dto.ReportDto;
import java.time.LocalDate;
import reactor.core.publisher.Flux;

/** Service for Reports. */
public interface ReportService {

  /**
   * Get an account status specifying a date range and a client.
   *
   * @param id Client ID.
   * @param start date to filter.
   * @param end date to filter.
   * @return a Flux with all accounts and movements into the date range.
   */
  Flux<ReportDto> getReport(Long id, LocalDate start, LocalDate end);
}
