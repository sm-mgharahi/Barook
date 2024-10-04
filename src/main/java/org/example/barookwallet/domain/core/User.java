package org.example.barookwallet.domain.core;

import lombok.Getter;
import org.example.barookwallet.domain.repositories.WalletRepository;

@Getter
public class User {
    private Long userId;
    private Wallet wallet;

    public User(Long userId, WalletRepository repository) {
        this.userId = userId;
        this.wallet = new Wallet(this, repository);
    }

    public User(Long userId) {
        this(userId, null);
    }
}