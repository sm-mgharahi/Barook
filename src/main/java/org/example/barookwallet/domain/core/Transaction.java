package org.example.barookwallet.domain.core;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class Transaction {
    private String id;
    private User owner;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionType type;

    public static Transaction getADebit(BigDecimal amount, Long userId) {
        return createATransaction(amount, userId, TransactionType.DEBIT);
    }

    public static Transaction getACredit(BigDecimal amount, Long userId) {
        return createATransaction(amount, userId, TransactionType.CREDIT);
    }

    private static Transaction createATransaction(BigDecimal amount, Long userId, TransactionType transactionType) {
        return Transaction.builder()
                .amount(amount)
                .date(LocalDateTime.now())
                .owner(new User(userId))
                .type(transactionType)
                .build();
    }
}
