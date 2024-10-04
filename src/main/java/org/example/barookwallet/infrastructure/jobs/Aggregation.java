package org.example.barookwallet.infrastructure.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.barookwallet.infrastructure.spring.repositories.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
@AllArgsConstructor
public class Aggregation {
    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void getTotalTransactionAmount() {
        log.info("total transaction amount: {}", transactionRepository.getTotalTransactionAmount());
    }
}