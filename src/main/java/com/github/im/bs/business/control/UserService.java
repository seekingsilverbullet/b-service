/*
 * 3/15/20, 12:45 PM
 * ivan
 */

package com.github.im.bs.business.control;

import com.github.im.bs.business.entity.User;
import com.github.im.bs.business.entity.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class UserService {
    private Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());
    private static final AtomicLong CLIENT_ID_HOLDER = new AtomicLong();

    @PostConstruct
    private void init() {
        long id = CLIENT_ID_HOLDER.incrementAndGet();
        users.put(id, new User(id, "Ivan", "Ivanov", UserType.ADMIN));
        id = CLIENT_ID_HOLDER.incrementAndGet();
        users.put(id, new User(id, "Petr", "Petrov", UserType.USER));
        id = CLIENT_ID_HOLDER.incrementAndGet();
        users.put(id, new User(id, "Alexander", "Alexandrov", UserType.USER));
        log.info("Users initialized");
    }

    public List<User> getAllUsers() {
        log.info("All users retrieved. Amount: {}", users.size());
        return new ArrayList<>(users.values());
    }

    public User getUser(Long userId) {
        log.info("Retrieved user by id: {}", userId);
        return users.get(userId);
    }

    public long create(User user) {
        long id = CLIENT_ID_HOLDER.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return id;
    }

    public boolean update(User user, long userId) {
        User currentUser = users.get(userId);
        if (currentUser != null) {
            user.setId(userId);
            users.put(userId, user);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(long userId) {
        User currentUser = users.get(userId);
        if (currentUser != null) {
            users.remove(userId);
            return true;
        } else {
            return false;
        }
    }
}
