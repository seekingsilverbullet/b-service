/*
 * 3/15/20, 12:32 PM
 * ivan
 */

package com.github.im.bs.business.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.github.im.bs.business.util.Constants.USER;

@Data
@NoArgsConstructor
@ApiModel(value = USER)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @ApiModelProperty(hidden = true)
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;
}
