package org.example.barookwallet.infrastructure.spring.repositories;

import jakarta.persistence.LockModeType;
import org.example.barookwallet.infrastructure.entities.Balance;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Balance b where b.userId = :userId")
    Optional<Balance> findByUserIdWithLock(Long userId);

    Optional<Balance> findByUserId(Long userId);
}