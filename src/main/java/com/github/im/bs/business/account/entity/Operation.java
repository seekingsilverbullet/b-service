/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.entity;

import com.github.im.bs.business.user.entity.User;

import java.math.BigDecimal;

public class Operation {
    private OperationType type;
    private BigDecimal sum;
    private User recipient;
}
