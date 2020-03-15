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

@Slf4j
@Service
public class UserService {
    private Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    @PostConstruct
    private void init() {
        users.put(1L, new User(1L, "Ivan", "Ivanov", UserType.ADMIN));
        users.put(2L, new User(2L, "Petr", "Petrov", UserType.USER));
        users.put(3L, new User(3L, "Sidr", "Sidorov", UserType.USER));
        log.info("Users initialized");
    }

    public List<User> getAllUsers() {
        log.info("All users retrieved. Amount: {}", users.size());
        return new ArrayList<>(users.values());
    }
}
