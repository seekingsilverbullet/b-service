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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        log.info("All users have retrieved. Amount: {}", users.size());
        return new ArrayList<>(users);
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        User user = repository.findUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        log.info("The user has retrieved by id: {}", user);
        return user;
    }

    public long create(User user) {
        User currentUser = repository.save(user);
        log.info("The user has created: {}", currentUser);
        return currentUser.getId();
    }

    public void update(User user, long userId) {
        User currentUser = getUser(userId);
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUserType(user.getUserType());
        repository.save(currentUser);
        log.info("The user has updated: {}", currentUser);
    }

    public void delete(long userId) {
        User currentUser = getUser(userId);
        repository.delete(currentUser);
        log.info("The user has deleted: {}", currentUser);
    }
}
