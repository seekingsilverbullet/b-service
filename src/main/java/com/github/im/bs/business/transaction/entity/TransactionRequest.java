/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private String recipientId;
}
