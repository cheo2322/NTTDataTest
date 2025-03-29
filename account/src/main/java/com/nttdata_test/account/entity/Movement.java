package com.nttdata_test.account.entity;

import com.nttdata_test.account.entity.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;

  @Column(name = "account_type", nullable = false)
  private AccountType accountType;

  @Column(name = "movement_value", nullable = false)
  private Double movementValue;

  @Column(nullable = false)
  private Double balance;

  @Column(name = "account_id", nullable = false)
  private Long accountId;
}
