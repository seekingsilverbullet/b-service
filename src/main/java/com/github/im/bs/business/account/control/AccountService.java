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
public class AccountService {
    private final UserService userService;
    private final AccountRepository repository;

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        List<Account> accounts = repository.findAll();
        log.info("All accounts have retrieved. Amount: {}", accounts.size());
        return new ArrayList<>(accounts);
    }

    @Transactional(readOnly = true)
    public Account getAccount(long userId) {
        Account account = repository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        log.info("The account has retrieved by user id: {}", account);
        return account;
    }

    public void create(long userId, Account account) {
        Account currentAccount = repository.findAccountByUserId(userId);
        if (currentAccount != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account exists");
        }
        account.setUser(userService.getUser(userId));
        repository.save(account);
        log.info("The account has created: {}", account);
    }

    public void delete(long userId) {
        Account account = repository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account doesn't exist");
        }
        repository.delete(account);
        log.info("The account has deleted: {}", account);
    }

    public OperationResponse performOperation(long userId, Operation operation) {
        Account account = getAccount(userId);

        if (operation.getType() == OperationType.CHECK_BALANCE) {
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
