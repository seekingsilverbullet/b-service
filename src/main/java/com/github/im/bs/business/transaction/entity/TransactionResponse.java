/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import static com.github.im.bs.business.util.Constants.TRANSACTION_RESPONSE;

@Data
@Builder
@ApiModel(value = TRANSACTION_RESPONSE)
public class TransactionResponse {
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private BigDecimal currentBalance;
}
