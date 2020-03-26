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

import static com.github.im.bs.business.util.Constants.COMMISSION_CHARGING_FINISHED_MESSAGE;
import static com.github.im.bs.business.util.Constants.COMMISSION_CHARGING_STARTED_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {
    private final TransactionService transactionService;
    private static final String EVERY_FIRST_DAY_OF_MONTH = "0 0 0 1 * *";

    @Scheduled(cron = EVERY_FIRST_DAY_OF_MONTH)
    public void scheduleCommissionCharge() {
        log.info(COMMISSION_CHARGING_STARTED_MESSAGE, LocalDateTime.now());
        transactionService.performCommissionCharging();
        log.info(COMMISSION_CHARGING_FINISHED_MESSAGE, LocalDateTime.now());
    }
}
