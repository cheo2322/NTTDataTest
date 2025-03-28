package com.nttdata_test.account.entity;

import com.nttdata_test.account.entity.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_number", nullable = false, length = 6, unique = true)
  private String accountNumber;

  @Column(name = "account_type", nullable = false)
  private AccountType accountType;

  @Column(name = "initial_balance", nullable = false)
  private Double initialBalance;

  @Column(nullable = false)
  private Boolean status;

  @Column(name = "client_id", nullable = false)
  private Long clientId;
}
