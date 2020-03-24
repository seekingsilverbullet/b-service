/*
 * 3/17/20, 10:46 PM
 * ivan
 */

package com.github.im.bs.business.account.entity;

import com.github.im.bs.business.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@ApiModel(value = "Account")
@Entity
public class Account {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @ApiModelProperty(hidden = true)
    private UUID id;
    private BigDecimal balance;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ApiModelProperty(hidden = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
