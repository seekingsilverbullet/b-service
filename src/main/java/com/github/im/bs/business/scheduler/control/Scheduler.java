/*
 * 3/23/20, 11:24 PM
 * ivan
 */

package com.github.im.bs.business.scheduler.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    @Scheduled(cron = "0 0 1 1 * *")
    public void scheduleCommissionCharge() {

    }
}
