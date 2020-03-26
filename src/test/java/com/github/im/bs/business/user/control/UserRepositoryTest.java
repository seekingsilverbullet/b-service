/*
 * 3/26/20, 2:15 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import com.github.im.bs.business.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    private DateTimeUtil dateTimeUtil = new DateTimeUtil();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = UserFactory.createTestUser();
        testUser.setCreatedAt(LocalDateTime.now());
        testUser = entityManager.persist(testUser);
        entityManager.flush();
    }

    @Test
    void findUserById_success() {
        User foundUser = userRepository.findUserById(testUser.getId());

        assertEquals(testUser, foundUser);
    }

    @Test
    void findUserById_not_found() {
        User foundUser = userRepository.findUserById(testUser.getId() + 1);

        assertNull(foundUser);
    }

    @Test
    void findCreatedUsersByPeriod_success() {
        Month month = LocalDateTime.now().getMonth();
        LocalDateTime startTime = dateTimeUtil.monthStartTime(month);
        LocalDateTime endTime = dateTimeUtil.monthEndTime(month);

        List<User> users = userRepository.findCreatedUsersByPeriod(startTime, endTime);

        assertFalse(users.isEmpty());
        assertEquals(testUser, users.get(0));
    }

    @Test
    void findCreatedUsersByPeriod_not_found() {
        Month month = LocalDateTime.now().getMonth().plus(1);
        LocalDateTime startTime = dateTimeUtil.monthStartTime(month);
        LocalDateTime endTime = dateTimeUtil.monthEndTime(month);

        List<User> users = userRepository.findCreatedUsersByPeriod(startTime, endTime);

        assertTrue(users.isEmpty());
    }
}
