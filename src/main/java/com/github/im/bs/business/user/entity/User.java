/*
 * 3/15/20, 12:32 PM
 * ivan
 */

package com.github.im.bs.business.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ApiModel(value = "User")
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
}
