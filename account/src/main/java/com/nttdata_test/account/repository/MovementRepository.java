package com.nttdata_test.account.repository;

import com.nttdata_test.account.entity.Movement;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

  Flux<Movement> findByAccountId(Long accountId);

  @Query("SELECT * FROM movement ORDER BY movement_date DESC LIMIT 1")
  Mono<Movement> findLastMovementByDate();

  @Query(
      "SELECT * FROM movement WHERE account_id = :accountId AND movement_date BETWEEN :startDate AND :endDate")
  Flux<Movement> findByAccountIdAndDateRange(
      Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
