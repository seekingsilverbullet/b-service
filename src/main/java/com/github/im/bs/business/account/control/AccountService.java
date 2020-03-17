/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.user.control.UserService;
import com.github.im.bs.business.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;

    public Account getAccount(long userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            return user.getUserAccount();
        } else {
            return null;
        }
    }

    public boolean create(long userId, Account account) {
        if (account != null) {
            User currentUser = userService.getUser(userId);
            if (currentUser != null) {
                currentUser.setUserAccount(account);
                userService.update(currentUser);
                log.info("User account '{}' has updated: {}", currentUser.getId(), account);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean delete(long userId) {
        User currentUser = userService.getUser(userId);
        if (currentUser != null) {
            currentUser.setUserAccount(null);
            userService.update(currentUser);
            log.info("User account '{}' has deleted.", currentUser.getId());
            return true;
        } else {
            return false;
        }
    }
}
