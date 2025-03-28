package com.nttdata_test.account.repository;

import com.nttdata_test.account.entity.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {}
