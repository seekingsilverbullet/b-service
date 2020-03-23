/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.entity;

import com.github.im.bs.business.user.entity.User;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class OperationRequest {
    private OperationType type;
    private BigDecimal sum;
    private User recipient;
}
