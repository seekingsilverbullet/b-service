/*
 * 3/26/20, 5:18 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import com.github.im.bs.business.user.entity.UserType;

public class UserFactory {
    public static User createTestUser() {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUserType(UserType.ADMIN);
        return user;
    }
}
