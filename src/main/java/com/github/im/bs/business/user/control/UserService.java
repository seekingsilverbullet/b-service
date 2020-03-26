/*
 * 3/15/20, 12:45 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.github.im.bs.business.util.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        log.info(USERS_RETRIEVED_MESSAGE, users.size());
        return Collections.unmodifiableList(users);
    }

    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE);
        }
        log.info(USER_RETRIEVED_MESSAGE, user);
        return user;
    }

    public long createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        User currentUser = userRepository.save(user);
        log.info(USER_CREATED_MESSAGE, currentUser);
        return currentUser.getId();
    }

    public void updateUser(User user, long userId) {
        User currentUser = findUser(userId);
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUserType(user.getUserType());
        userRepository.save(currentUser);
        log.info(USER_UPDATED_MESSAGE, currentUser);
    }

    public void deleteUser(long userId) {
        User currentUser = findUser(userId);
        userRepository.delete(currentUser);
        log.info(USER_DELETED_MESSAGE, currentUser);
    }

    @Transactional(readOnly = true)
    public List<User> findCreatedUsersByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.unmodifiableList(userRepository.findCreatedUsersByPeriod(startDate, endDate));
    }
}
