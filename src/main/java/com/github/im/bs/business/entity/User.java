/*
 * 3/15/20, 12:32 PM
 * ivan
 */

package com.github.im.bs.business.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel(value = "User")
@Entity
public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
}
