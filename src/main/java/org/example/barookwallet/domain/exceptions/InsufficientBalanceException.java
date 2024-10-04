package org.example.barookwallet.domain.exceptions;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InsufficientBalanceException extends RuntimeException {
    private final Long userId;
    private final BigDecimal balance;
    private final BigDecimal updateRequestAmount;

    public InsufficientBalanceException(Long userId, BigDecimal balance, BigDecimal updateRequestAmount) {
        this.userId = userId;
        this.balance = balance;
        this.updateRequestAmount = updateRequestAmount;
    }
}
