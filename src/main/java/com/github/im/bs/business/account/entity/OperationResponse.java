/*
 * 3/21/20, 6:21 PM
 * ivan
 */

package com.github.im.bs.business.account.entity;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class OperationResponse {
    private OperationType type;
    private BigDecimal currentBalance;
    private boolean performed;
    private String message;
}
