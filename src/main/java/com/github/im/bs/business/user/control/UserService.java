/*
 * 3/15/20, 12:45 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        log.info("All users retrieved. Amount: {}", users.size());
        return new ArrayList<>(users);
    }

    public User getUser(Long userId) {
        log.info("Retrieved user by id: {}", userId);
        return repository.findUserById(userId);
    }

    public long create(User user) {
        User savedUser = repository.save(user);
        log.info("Created new User: {}", savedUser);
        return savedUser.getId();
    }

    public boolean update(User user, long userId) {
        User currentUser = repository.findUserById(userId);
        if (currentUser != null) {
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setUserType(user.getUserType());
            currentUser.setUserAccount(user.getUserAccount());
            repository.saveAndFlush(currentUser);
            log.info("User '{}' has updated: {}", currentUser.getId(), currentUser);
            return true;
        } else {
            return false;
        }
    }

    public void update(@NonNull User user) {
        repository.saveAndFlush(user);
        log.info("User '{}' has updated: {}", user.getId(), user);
    }

    public boolean delete(long userId) {
        User currentUser = repository.findUserById(userId);
        if (currentUser != null) {
            repository.delete(currentUser);
            log.info("User '{}' has deleted.", currentUser.getId());
            return true;
        } else {
            return false;
        }
    }
}
