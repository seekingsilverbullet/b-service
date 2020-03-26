/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import static com.github.im.bs.business.util.Constants.TRANSACTION_REQUEST;

@Data
@AllArgsConstructor
@ApiModel(value = TRANSACTION_REQUEST)
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private String recipientId;
}
