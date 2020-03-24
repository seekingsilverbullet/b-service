/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private BigDecimal currentBalance;
}
