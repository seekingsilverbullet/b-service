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
import com.github.im.bs.business.util.DateTimeUtil;
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
    private final DateTimeUtil dateTimeUtil;

    public List<Account> findAllAccounts() {
        return accountService.findAllAccounts();
    }

    public List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    public Report createMonthReport(Month month) {
        Report report = new Report();
        LocalDateTime startTime = dateTimeUtil.monthStartTime(month);
        LocalDateTime endTime = dateTimeUtil.monthEndTime(month);

        report.setUsersCreated(userService.findCreatedUsersByPeriod(startTime, endTime));
        report.setAccountsCreated(accountService.findCreatedAccountsByPeriod(startTime, endTime));
        report.setTransactionsPerformed(transactionService.findCreatedTransactionsByPeriod(startTime, endTime));
        return report;
    }

    public void performCommissionCharging() {
        transactionService.performCommissionCharging();
    }
}
