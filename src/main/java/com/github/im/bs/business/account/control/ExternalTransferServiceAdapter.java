/*
 * 3/23/20, 3:51 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Slf4j
@Component
@Transactional
public class ExternalTransferServiceAdapter {
    public void performExternalTransfer(String recipientId, BigDecimal operationSum) {
        try {
            log.info("Performed external transfer request: '{}' -> {}", recipientId, operationSum);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "The transfer was failed due to exception", e);
        }
    }
}
