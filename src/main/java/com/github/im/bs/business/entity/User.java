/*
 * 3/15/20, 12:32 PM
 * ivan
 */

package com.github.im.bs.business.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
}
