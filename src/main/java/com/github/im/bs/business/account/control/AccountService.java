/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.account.entity.Operation;
import com.github.im.bs.business.account.entity.OperationResponse;
import com.github.im.bs.business.account.entity.OperationType;
import com.github.im.bs.business.user.control.UserService;
import com.github.im.bs.business.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;

    public Account getAccount(long userId) {
        User user = userService.getUser(userId);
        Account userAccount = user.getUserAccount();
        if (userAccount == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return userAccount;
    }

    public void create(long userId, Account account) {
        User currentUser = userService.getUser(userId);
        currentUser.setUserAccount(account);
        userService.update(currentUser);
        log.info("User account '{}' has updated: {}", currentUser.getId(), account);
    }

    public void delete(long userId) {
        User currentUser = userService.getUser(userId);
        currentUser.setUserAccount(null);
        userService.update(currentUser);
        log.info("User account '{}' has deleted.", currentUser.getId());
    }

    public OperationResponse performOperation(long userId, Operation operation) {
        if (operation.getType() == OperationType.CHECK_BALANCE) {
            Account account = getAccount(userId);
            return OperationResponse.builder()
                    .type(operation.getType())
                    .currentBalance(account.getBalance())
                    .performed(true)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Operation is not supported");
        }
    }
}
