package org.example.barookwallet.infrastructure.spring.repositories;

import org.example.barookwallet.infrastructure.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    List<Transaction> findByUserId(Long userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal getTotalTransactionAmount();
}