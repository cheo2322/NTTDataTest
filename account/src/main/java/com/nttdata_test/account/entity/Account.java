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

  @Column(nullable = false, length = 6)
  private String accountNumber;

  @Column(nullable = false)
  private AccountType accountType;

  @Column(nullable = false)
  private Double initialBalance;

  @Column(nullable = false)
  private Boolean status;
}
