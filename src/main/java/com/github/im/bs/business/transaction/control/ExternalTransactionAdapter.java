/*
 * 3/24/20, 4:38 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@Transactional
public class ExternalTransactionAdapter {
    public void performExternalTransfer(String recipientId, BigDecimal transferSum) {
        log.info("Performed external transfer request: '{}' -> {}", recipientId, transferSum);
    }
}
