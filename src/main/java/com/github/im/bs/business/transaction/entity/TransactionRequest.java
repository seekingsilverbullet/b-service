/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private String recipientId;
}
