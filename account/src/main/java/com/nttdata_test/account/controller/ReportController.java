package com.nttdata_test.account.controller;

import com.nttdata_test.account.entity.dto.ReportDto;
import com.nttdata_test.account.service.ReportService;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/nttdata/test/v1/reports")
public class ReportController {

  public final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<ReportDto> getReport(
      @RequestParam String clientId, @RequestParam LocalDate start, @RequestParam LocalDate end) {
    return reportService.getReport(clientId, start, end);
  }
}
