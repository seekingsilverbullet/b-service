/*
 * 3/15/20, 11:08 PM
 * ivan
 */

package com.github.im.bs.business.control;

import com.github.im.bs.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long userId);
}
