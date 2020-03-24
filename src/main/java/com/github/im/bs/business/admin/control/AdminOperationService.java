/*
 * 3/24/20, 8:06 PM
 * ivan
 */

package com.github.im.bs.business.admin.control;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.admin.entity.Report;
import com.github.im.bs.business.transaction.control.TransactionService;
import com.github.im.bs.business.transaction.entity.Transaction;
import com.github.im.bs.business.user.control.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminOperationService {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;

    public List<Account> findAllAccounts() {
        return accountService.findAllAccounts();
    }

    public List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    public Report createMonthReport(Month month) {
        Report report = new Report();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), month, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(now.getYear(), month.plus(1), 1, 0, 0, 0);

        report.setUsersCreated(userService.findCreatedUsersByPeriod(startDate, endDate));
        report.setAccountsCreated(accountService.findCreatedAccountsByPeriod(startDate, endDate));
        report.setTransactionsPerformed(transactionService.findCreatedTransactionsByPeriod(startDate, endDate));
        return report;
    }
}
