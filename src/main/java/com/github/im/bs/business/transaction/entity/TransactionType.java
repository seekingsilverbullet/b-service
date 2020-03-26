/*
 * 3/24/20, 4:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import io.swagger.annotations.ApiModel;

import static com.github.im.bs.business.util.Constants.TRANSACTION_TYPE;

@ApiModel(value = TRANSACTION_TYPE)
public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER_INTERNAL,
    TRANSFER_EXTERNAL
}
