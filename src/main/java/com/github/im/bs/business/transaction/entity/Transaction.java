/*
 * 3/23/20, 7:16 PM
 * ivan
 */

package com.github.im.bs.business.transaction.entity;

import com.github.im.bs.business.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(value = "Transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @ApiModelProperty(hidden = true)
    private Long id;
    private TransactionType transactionType;
    private BigDecimal transactionSum;
    private BigDecimal balanceBeforeTransaction;
    private BigDecimal balanceAfterTransaction;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;
    private String userReferenceId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ApiModelProperty(hidden = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
