package org.example.barookwallet.infrastructure.repositories;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.barookwallet.domain.core.Transaction;
import org.example.barookwallet.domain.repositories.WalletRepository;
import org.example.barookwallet.infrastructure.entities.Balance;
import org.example.barookwallet.infrastructure.entities.TransactionType;
import org.example.barookwallet.infrastructure.exceptions.UserNotFoundException;
import org.example.barookwallet.infrastructure.spring.repositories.BalanceRepository;
import org.example.barookwallet.infrastructure.spring.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class Wallet implements WalletRepository {

    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Long updateBalance(Transaction transaction) {
        var userBalance = balanceRepository
                .findByUserIdWithLock(transaction.getOwner().getUserId())
                .orElse(new Balance(transaction.getOwner().getUserId(), BigDecimal.ZERO));

        userBalance.setBalance(userBalance.getBalance().add(transaction.getAmount()));
        balanceRepository.save(userBalance);

        org.example.barookwallet.infrastructure.entities.Transaction dbTransaction = new org.example.barookwallet.infrastructure.entities.Transaction();
        dbTransaction.setAmount(transaction.getAmount().abs());
        dbTransaction.setDate(LocalDateTime.now());
        dbTransaction.setType(TransactionType.valueOf(transaction.getType().name()));
        dbTransaction.setUserId(transaction.getOwner().getUserId());

        return transactionRepository.save(dbTransaction).getId();
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        return balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .getBalance();
    }


}