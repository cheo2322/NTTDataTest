package com.nttdata_test.account.entity;

import com.nttdata_test.account.entity.enums.AccountType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "movement")
@Data
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;

  @Column(name = "account_type", nullable = false)
  private AccountType accountType;

  @Column(name = "movement_value", nullable = false)
  private Double value;

  @Column(nullable = false)
  private Double balance;

  @Column(name = "account_id", nullable = false)
  private Long accountId;
}
