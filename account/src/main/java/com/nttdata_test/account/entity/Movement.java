package com.nttdata_test.account.entity;

import com.nttdata_test.account.entity.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "movement")
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private LocalDateTime movementDate;

    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
}

