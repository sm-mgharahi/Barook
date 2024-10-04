package org.example.barookwallet.domain.core;

import org.example.barookwallet.domain.exceptions.InsufficientBalanceException;
import org.example.barookwallet.domain.repositories.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Wallet {
    private final User owner;
    private final WalletRepository repository;

    public Wallet(User owner, WalletRepository repository) {
        this.owner = owner;
        this.repository = repository;
    }

    public void loadTransactions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BigDecimal getBalance() {
        return repository.getBalance(owner.getUserId());
    }

    public Long deposit(BigDecimal amount) {
        Transaction transaction = Transaction.getACredit(amount, owner.getUserId());
        return repository.updateBalance(transaction);
    }

    public Long withdraw(BigDecimal amount) {
        if (getBalance().compareTo(amount.abs()) < 0)
            throw new InsufficientBalanceException(owner.getUserId(), getBalance(), amount.abs());

        Transaction transaction = Transaction.getADebit(amount, owner.getUserId());
        return repository.updateBalance(transaction);
    }
}