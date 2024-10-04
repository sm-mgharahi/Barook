package org.example.barookwallet.domain.repositories;

import org.example.barookwallet.domain.core.Transaction;

import java.math.BigDecimal;

public interface WalletRepository {
    Long updateBalance(Transaction transaction);

    BigDecimal getBalance(Long userId);
}