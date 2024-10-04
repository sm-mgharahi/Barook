package org.example.barookwallet.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Balances")
@Getter
@Setter
public class Balance {

    public Balance(){}

    public Balance(Long userId,BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "BALANCE",precision = 10)
    private BigDecimal balance;
}