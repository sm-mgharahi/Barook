package org.example.barookwallet.application.services;

import lombok.AllArgsConstructor;
import org.example.barookwallet.domain.core.User;
import org.example.barookwallet.domain.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class WalletServices {
    private final WalletRepository walletRepository;

    public BigDecimal getBalance(Long userId) {
        User client = new User(userId, walletRepository);
        return client.getWallet().getBalance();
    }

    public Long updateBalance(Long userId, BigDecimal amount) {
        User client = new User(userId, walletRepository);
        if (amount.compareTo(BigDecimal.valueOf(0)) > 0)
            return client.getWallet().deposit(amount);
        else if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
            return client.getWallet().withdraw(amount);
        else
            return 0L;

    }
}