/*
 * 3/23/20, 7:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.transaction.entity.TransactionRequest;
import com.github.im.bs.business.transaction.entity.TransactionResponse;
import com.github.im.bs.business.transaction.entity.TransactionType;
import com.github.im.bs.business.transaction.entity.Transaction;
import com.github.im.bs.business.user.entity.User;
import com.github.im.bs.business.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ExternalTransactionAdapter externalTransactionAdapter;
    private final AccountService accountService;
    private final DateTimeUtil dateTimeUtil;

    private static final BigDecimal EXTERNAL_TRANSFER_COMMISSION_VALUE = new BigDecimal("0.01");

    @Transactional(readOnly = true)
    public List<Transaction> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        log.info("All transactions have retrieved. Amount: {}", transactions.size());
        return Collections.unmodifiableList(transactions);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findUserTransactions(long userId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByUserId(userId);
        log.info("All user '{}' transactions have retrieved. Amount: {}", userId, transactions.size());
        return Collections.unmodifiableList(transactions);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findCreatedTransactionsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.unmodifiableList(transactionRepository.findCreatedTransactionsByPeriod(startDate, endDate));
    }

    public void performCommissionCharging() {
        Month month = LocalDateTime.now().minusMonths(1).getMonth();
        LocalDateTime startTime = dateTimeUtil.monthStartTime(month);
        LocalDateTime endTime = dateTimeUtil.monthEndTime(month);

        List<Transaction> monthTransactions = findCreatedTransactionsByPeriod(startTime, endTime);
        Map<User, BigDecimal> volumeByUser = calculateVolumeByUser(monthTransactions);
        accountService.findAllAccounts().forEach(account -> {
            chargeCommission(volumeByUser, account);
        });
    }

    public TransactionResponse performTransaction(long userId, TransactionRequest request) {
        try {
            Account account = accountService.findAccount(userId);
            switch (request.getTransactionType()) {
                case WITHDRAW:
                    return performWithdraw(request, account, null);
                case DEPOSIT:
                    return performDeposit(request, account, null);
                case TRANSFER_INTERNAL:
                    return performInternalTransfer(request, account);
                case TRANSFER_EXTERNAL:
                    return performExternalTransfer(request, account);
                default:
                    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Transaction type is not supported");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction execution is failed", e);
        }
    }

    private TransactionResponse performWithdraw(TransactionRequest request, Account account, Transaction transaction) {
        log.info("Trying to perform transaction for user '{}': {}", account.getUser().getId(), request);
        BigDecimal resultBalance = account.getBalance().subtract(request.getTransactionSum());
        if (resultBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }

        return performTransaction(request, account, transaction, resultBalance);
    }

    private TransactionResponse performDeposit(TransactionRequest request, Account account, Transaction transaction) {
        log.info("Trying to perform transaction for user '{}': {}", account.getUser().getId(), request);
        BigDecimal resultBalance = account.getBalance().add(request.getTransactionSum());

        return performTransaction(request, account, transaction, resultBalance);
    }

    private TransactionResponse performInternalTransfer(TransactionRequest request, Account account) {
        log.info("Trying to perform transaction for user '{}': {}", account.getUser().getId(), request);
        Account recipient = accountService.findAccount(getUserIdFromRequest(request));

        performWithdraw(request, account, createTransaction(request, account));

        Transaction transaction = createTransaction(request, recipient);
        transaction.setUserReferenceId(String.valueOf(account.getUser().getId()));
        performDeposit(request, recipient, transaction);

        return createResponse(request.getTransactionType(), request.getTransactionSum(), account.getBalance());
    }

    private TransactionResponse performExternalTransfer(TransactionRequest request, Account account) {
        log.info("Trying to perform operation for user '{}': {}", account.getUser().getId(), request);
        BigDecimal transactionSum = request.getTransactionSum();
        BigDecimal commissionSum = transactionSum.multiply(EXTERNAL_TRANSFER_COMMISSION_VALUE);

        Transaction transaction = createTransaction(request, account);
        request.setTransactionSum(transactionSum.add(commissionSum));
        performWithdraw(request, account, transaction);
        externalTransactionAdapter.performExternalTransfer(request.getRecipientId(), transactionSum);

        return createResponse(request.getTransactionType(), transactionSum, account.getBalance());
    }

    private TransactionResponse performTransaction(TransactionRequest request, Account account,
                                                   Transaction transaction, BigDecimal resultBalance) {
        resultBalance = resultBalance.setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal originalBalance = account.getBalance();
        account.setBalance(resultBalance);
        accountService.updateAccount(account);

        if (transaction == null) {
            transaction = createTransaction(request, account);
            transaction.setBalanceBeforeTransaction(originalBalance);
        }
        transaction.setBalanceAfterTransaction(resultBalance);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        log.info("The transaction has registered: {}", transaction);

        return createResponse(request.getTransactionType(), request.getTransactionSum(), account.getBalance());
    }

    private long getUserIdFromRequest(TransactionRequest request) {
        try {
            return Long.parseLong(request.getRecipientId());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
    }

    private Transaction createTransaction(TransactionRequest request, Account account) {
        return Transaction.builder()
                .transactionType(request.getTransactionType())
                .transactionSum(request.getTransactionSum())
                .balanceBeforeTransaction(account.getBalance())
                .userReferenceId(request.getRecipientId())
                .user(account.getUser())
                .build();
    }

    private TransactionResponse createResponse(TransactionType type, BigDecimal sum, BigDecimal balance) {
        return TransactionResponse.builder()
                .transactionType(type)
                .transactionSum(sum)
                .currentBalance(balance)
                .build();
    }

    private void chargeCommission(Map<User, BigDecimal> volumeByUser, Account account) {
        BigDecimal commission = BigDecimal.ZERO;
        User user = account.getUser();
        if (volumeByUser.containsKey(user)) {
            BigDecimal userVolume = volumeByUser.get(user);
            if (userVolume.compareTo(new BigDecimal(30000)) <= 0) {
                commission = new BigDecimal(200);
            }
        } else {
            commission = new BigDecimal(100);
        }

        if (commission.compareTo(BigDecimal.ZERO) > 0) {
            performTransaction(user.getId(),
                    new TransactionRequest(TransactionType.WITHDRAW, commission, "commission"));
        }
    }

    private Map<User, BigDecimal> calculateVolumeByUser(List<Transaction> monthTransactions) {
        Map<User, List<Transaction>> transactionByUser = monthTransactions.stream().collect(groupingBy(Transaction::getUser));
        Map<User, BigDecimal> volumeByUser = new HashMap<>();
        for (Map.Entry<User, List<Transaction>> entry : transactionByUser.entrySet()) {
            Stream<Transaction> userTransactions = entry.getValue().stream();
            BigDecimal volume = userTransactions.map(Transaction::getTransactionSum).reduce(BigDecimal.ZERO, BigDecimal::add);
            volumeByUser.put(entry.getKey(), volume);
        }
        return volumeByUser;
    }
}
