/*
 * 3/17/20, 10:46 PM
 * ivan
 */

package com.github.im.bs.business.account.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel(value = "Account")
@Entity
public class Account {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @ApiModelProperty(hidden = true)
    private UUID id;
    private BigDecimal balance;
}
