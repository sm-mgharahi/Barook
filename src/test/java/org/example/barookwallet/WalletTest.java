package org.example.barookwallet;

import org.example.barookwallet.domain.core.User;
import org.example.barookwallet.infrastructure.entities.Balance;
import org.example.barookwallet.domain.core.Transaction;
import org.example.barookwallet.domain.core.TransactionType;
import org.example.barookwallet.infrastructure.repositories.Wallet;
import org.example.barookwallet.infrastructure.spring.repositories.BalanceRepository;
import org.example.barookwallet.infrastructure.spring.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.example.barookwallet.infrastructure.exceptions.UserNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBalance_NewUser() {
        Long userId = 1L;
        BigDecimal transactionAmount = new BigDecimal("100.00");

        Transaction transaction = Transaction.builder()
                .amount(transactionAmount)
                .owner(new User(userId))
                .type(TransactionType.CREDIT)
                .build();

        Balance newUserBalance = new Balance(userId, BigDecimal.ZERO);


        when(balanceRepository.findByUserIdWithLock(userId)).thenReturn(Optional.empty());
        when(balanceRepository.save(any(Balance.class))).thenReturn(newUserBalance);

        org.example.barookwallet.infrastructure.entities.Transaction dbTransaction = new org.example.barookwallet.infrastructure.entities.Transaction();
        dbTransaction.setId(123L);

        when(transactionRepository.save(any(org.example.barookwallet.infrastructure.entities.Transaction.class))).thenReturn(dbTransaction);

        Long transactionId = wallet.updateBalance(transaction);


        assertEquals(123L, transactionId);
        verify(balanceRepository).findByUserIdWithLock(userId);
        verify(balanceRepository).save(any(Balance.class));
        verify(transactionRepository).save(any(org.example.barookwallet.infrastructure.entities.Transaction.class));
    }

    @Test
    void testUpdateBalance_ExistingUser() {
        Long userId = 1L;
        BigDecimal transactionAmount = new BigDecimal("50.00");
        BigDecimal initialBalance = new BigDecimal("100.00");

        Transaction transaction = Transaction.builder()
                .amount(transactionAmount)
                .owner(new User(userId))
                .type(TransactionType.DEBIT)
                .build();


        Balance existingBalance = new Balance(userId, initialBalance);

        when(balanceRepository.findByUserIdWithLock(userId)).thenReturn(Optional.of(existingBalance));

        org.example.barookwallet.infrastructure.entities.Transaction dbTransaction = new org.example.barookwallet.infrastructure.entities.Transaction();
        dbTransaction.setId(456L);

        when(transactionRepository.save(any(org.example.barookwallet.infrastructure.entities.Transaction.class))).thenReturn(dbTransaction);

        Long transactionId = wallet.updateBalance(transaction);

        assertEquals(456L, transactionId);
        assertEquals(new BigDecimal("150.00"), existingBalance.getBalance());
        verify(balanceRepository).findByUserIdWithLock(userId);
        verify(balanceRepository).save(existingBalance);
        verify(transactionRepository).save(any(org.example.barookwallet.infrastructure.entities.Transaction.class));
    }

    @Test
    void testGetBalance_Success() {
        Long userId = 1L;
        BigDecimal balance = new BigDecimal("200.00");

        Balance userBalance = new Balance(userId, balance);

        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.of(userBalance));

        BigDecimal retrievedBalance = wallet.getBalance(userId);

        assertEquals(balance, retrievedBalance);
        verify(balanceRepository).findByUserId(userId);
    }

    @Test
    void testGetBalance_UserNotFound() {
        Long userId = 1L;

        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            wallet.getBalance(userId);
        });

        assertEquals("User not found: " + userId, exception.getMessage());
        verify(balanceRepository).findByUserId(userId);
    }
}
