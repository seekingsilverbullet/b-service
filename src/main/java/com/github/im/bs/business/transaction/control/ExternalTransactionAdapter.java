/*
 * 3/24/20, 4:38 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.github.im.bs.business.util.Constants.EXTERNAL_TRANSACTION_PERFORMED_MESSAGE;

@Slf4j
@Component
@Transactional
public class ExternalTransactionAdapter {
    public void performExternalTransfer(String recipientId, BigDecimal transferSum) {
        log.info(EXTERNAL_TRANSACTION_PERFORMED_MESSAGE, recipientId, transferSum);
    }
}
