/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.user.control.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.github.im.bs.business.util.Constants.*;

@Slf4j
@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<Account> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        log.info(ACCOUNTS_RETRIEVED_MESSAGE, accounts.size());
        return Collections.unmodifiableList(accounts);
    }

    @Transactional(readOnly = true)
    public Account findAccount(long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND);
        }
        log.info(ACCOUNT_RETRIEVED_MESSAGE, account);
        return account;
    }

    public void createAccount(long userId, Account account) {
        Account currentAccount = accountRepository.findAccountByUserId(userId);
        if (currentAccount != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ACCOUNT_EXISTS);
        }
        account.setCreatedAt(LocalDateTime.now());
        account.setUser(userService.findUser(userId));
        accountRepository.save(account);
        log.info(ACCOUNT_CREATED_MESSAGE, account);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
        log.info(ACCOUNT_UPDATED_MESSAGE, account);
    }

    public void deleteAccount(long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ACCOUNT_NOT_EXISTS);
        }
        accountRepository.delete(account);
        log.info(ACCOUNT_DELETED_MESSAGE, account);
    }

    @Transactional(readOnly = true)
    public BigDecimal checkBalance(long userId) {
        BigDecimal balance = findAccount(userId).getBalance();
        log.info(ACCOUNT_BALANCE_RETRIEVED_MESSAGE, userId, balance);
        return balance;
    }

    @Transactional(readOnly = true)
    public List<Account> findCreatedAccountsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        List<Account> createdAccountsByPeriod = accountRepository.findCreatedAccountsByPeriod(startDate, endDate);
        log.info(ALL_ACCOUNTS_BY_PERIOD_MESSAGE, startDate, endDate);
        return Collections.unmodifiableList(createdAccountsByPeriod);
    }
}
