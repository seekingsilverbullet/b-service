/*
 * 3/15/20, 12:32 PM
 * ivan
 */

package com.github.im.bs.business.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@ApiModel(value = "User")
public class User {
    @Setter
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
}
