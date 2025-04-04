package com.nttdata_test.person.repository;

import com.nttdata_test.person.entity.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {

  Mono<Client> findByClientId(String clientID);
}
