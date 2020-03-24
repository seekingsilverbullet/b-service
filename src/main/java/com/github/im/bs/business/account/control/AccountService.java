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
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<Account> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        log.info("All accounts have retrieved. Amount: {}", accounts.size());
        return Collections.unmodifiableList(accounts);
    }

    @Transactional(readOnly = true)
    public Account findAccount(long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        log.info("The account has retrieved by user id: {}", account);
        return account;
    }

    public void createAccount(long userId, Account account) {
        Account currentAccount = accountRepository.findAccountByUserId(userId);
        if (currentAccount != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account exists");
        }
        account.setCreatedAt(LocalDateTime.now());
        account.setUser(userService.findUser(userId));
        accountRepository.save(account);
        log.info("The account has created: {}", account);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
        log.info("The account has updated: {}", account);
    }

    public void deleteAccount(long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account doesn't exist");
        }
        accountRepository.delete(account);
        log.info("The account has deleted: {}", account);
    }

    @Transactional(readOnly = true)
    public BigDecimal checkBalance(long userId) {
        BigDecimal balance = findAccount(userId).getBalance();
        log.info("Retrieved account balance for user '{}': {}", userId, balance);
        return balance;
    }

    @Transactional(readOnly = true)
    public List<Account> findCreatedAccountsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.unmodifiableList(accountRepository.findCreatedAccountsByPeriod(startDate, endDate));
    }
}
