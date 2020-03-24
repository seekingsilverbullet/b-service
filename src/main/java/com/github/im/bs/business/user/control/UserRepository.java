/*
 * 3/15/20, 11:08 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long userId);

    @Query("select u from User u where u.createdAt >= :startDate and u.createdAt < :endDate order by u.id")
    List<User> findCreatedUsersByPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
