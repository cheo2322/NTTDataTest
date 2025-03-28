package com.nttdata_test.account.repository;

import com.nttdata_test.account.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

  Mono<Account> findByAccountNumber(String accountNumber);
}
