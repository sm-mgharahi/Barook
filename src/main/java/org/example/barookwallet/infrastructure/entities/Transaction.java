package org.example.barookwallet.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "AMOUNT",precision = 10)
    private BigDecimal amount;

    @Column(name = "DATE",precision = 10)
    private LocalDateTime date;

    @Column(name = "TYPE",precision = 10)
    private TransactionType type;
}
