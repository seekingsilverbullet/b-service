/*
 * 3/15/20, 12:35 PM
 * ivan
 */

package com.github.im.bs.business.user.entity;

import io.swagger.annotations.ApiModel;

import static com.github.im.bs.business.util.Constants.USER_TYPE;

@ApiModel(value = USER_TYPE)
public enum UserType {
    ADMIN,
    USER
}
