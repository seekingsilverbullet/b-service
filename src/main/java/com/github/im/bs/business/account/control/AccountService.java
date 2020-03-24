/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.account.entity.OperationRequest;
import com.github.im.bs.business.account.entity.OperationResponse;
import com.github.im.bs.business.account.entity.OperationType;
import com.github.im.bs.business.transaction.control.TransactionService;
import com.github.im.bs.business.transaction.entity.Transaction;
import com.github.im.bs.business.user.control.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository repository;
    private final UserService userService;
    private final TransactionService transactionService;
    private final ExternalTransferServiceAdapter transferAdapter;

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal EXTERNAL_TRANSFER_COMMISSION_VALUE = new BigDecimal("0.01");

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

    @Transactional(readOnly = true)
    public BigDecimal retrieveBalance(long userId) {
        BigDecimal balance = repository.retrieveBalanceByUserId(userId);
        if (balance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        log.info("Retrieved account balance for user '{}': {}", userId, balance);
        return balance;
    }

    public OperationResponse performOperation(long userId, OperationRequest request) {
        try {
            Account account = getAccount(userId);
            switch (request.getOperationType()) {
                case WITHDRAW:
                    return performWithdraw(request.getOperationSum(), account, null);
                case DEPOSIT:
                    return performDeposit(request.getOperationSum(), account, null);
                case TRANSFER:
                    return performTransfer(request, account);
                default:
                    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Operation is not supported");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Operation execution is failed", e);
        }
    }

    private OperationResponse performWithdraw(BigDecimal operationSum, Account account, Transaction transaction) {
        BigDecimal originalBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().subtract(operationSum);
        if (resultBalance.compareTo(ZERO) >= 0) {
            resultBalance = resultBalance.setScale(2, RoundingMode.HALF_DOWN);
            account.setBalance(resultBalance);
            repository.save(account);

            if (transaction == null) {
                transaction = Transaction.builder()
                        .operationType(OperationType.WITHDRAW)
                        .balanceBeforeTransaction(originalBalance)
                        .balanceAfterTransaction(resultBalance)
                        .user(account.getUser())
                        .build();
            } else {
                transaction.setBalanceAfterTransaction(resultBalance);
            }
            transactionService.register(transaction);

            OperationResponse response = OperationResponse.builder()
                    .type(OperationType.WITHDRAW)
                    .operationSum(operationSum)
                    .currentBalance(account.getBalance())
                    .build();
            log.info("Performed operation for user '{}': {}", account.getUser().getId(), response);
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }
    }

    private OperationResponse performDeposit(BigDecimal operationSum, Account account, Transaction transaction) {
        BigDecimal originalBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().add(operationSum);
        account.setBalance(resultBalance);
        repository.save(account);

        if (transaction == null) {
            transaction = Transaction.builder()
                    .operationType(OperationType.DEPOSIT)
                    .balanceBeforeTransaction(originalBalance)
                    .balanceAfterTransaction(resultBalance)
                    .user(account.getUser())
                    .build();
        } else {
            transaction.setBalanceAfterTransaction(resultBalance);
        }
        transactionService.register(transaction);

        OperationResponse response = OperationResponse.builder()
                .type(OperationType.DEPOSIT)
                .operationSum(operationSum)
                .currentBalance(account.getBalance())
                .build();
        log.info("Performed operation for user '{}': {}", account.getUser().getId(), response);
        return response;
    }

    private OperationResponse performTransfer(OperationRequest request, Account account) {
        switch (request.getServiceType()) {
            case INTERNAL:
                return performInternalTransfer(request, account);
            case EXTERNAL:
                return performExternalTransfer(request, account);
            default:
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Service type is not supported");
        }
    }

    private OperationResponse performInternalTransfer(OperationRequest request, Account account) {
        log.info("Trying to performed operation for user '{}': {}", account.getUser().getId(), request);
        Account recipient = getAccount(getUserIdFromRequest(request));
        Transaction transaction = Transaction.builder()
                .operationType(OperationType.TRANSFER)
                .balanceBeforeTransaction(account.getBalance())
                .user(account.getUser())
                .build();
        performWithdraw(request.getOperationSum(), account, transaction);
        transaction = Transaction.builder()
                .operationType(OperationType.TRANSFER)
                .balanceBeforeTransaction(recipient.getBalance())
                .user(recipient.getUser())
                .build();
        performDeposit(request.getOperationSum(), recipient, transaction);
        OperationResponse response = OperationResponse.builder()
                .type(request.getOperationType())
                .operationSum(request.getOperationSum())
                .currentBalance(account.getBalance())
                .build();
        log.info("Performed operation for user '{}': {}", account.getUser().getId(), response);
        return response;
    }

    private long getUserIdFromRequest(OperationRequest request) {
        try {
            return Long.parseLong(request.getRecipientId());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
    }

    private OperationResponse performExternalTransfer(OperationRequest request, Account account) {
        log.info("Trying to performed operation for user '{}': {}", account.getUser().getId(), request);
        BigDecimal operationSum = request.getOperationSum();
        BigDecimal commissionSum = operationSum.multiply(EXTERNAL_TRANSFER_COMMISSION_VALUE);

        Transaction transaction = Transaction.builder()
                .operationType(OperationType.TRANSFER)
                .balanceBeforeTransaction(account.getBalance())
                .user(account.getUser())
                .build();
        performWithdraw(operationSum.add(commissionSum), account, transaction);
        transferAdapter.performExternalTransfer(request.getRecipientId(), operationSum);

        OperationResponse response = OperationResponse.builder()
                .type(request.getOperationType())
                .operationSum(operationSum)
                .currentBalance(account.getBalance())
                .build();
        log.info("Performed operation for user '{}': {}", account.getUser().getId(), response);
        return response;
    }
}
