/*
 * 3/25/20, 7:23 PM
 * ivan
 */

package com.github.im.bs.config;

import com.github.im.bs.business.transaction.control.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {
    private final TransactionService transactionService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void scheduleCommissionCharge() {
        log.info("Scheduled commission charging started at {}", LocalDateTime.now());
        transactionService.performCommissionCharging();
        log.info("Scheduled commission charging finished at {}", LocalDateTime.now());
    }
}
