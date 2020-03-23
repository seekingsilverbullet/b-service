/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.account.entity.OperationRequest;
import com.github.im.bs.business.account.entity.OperationResponse;
import com.github.im.bs.business.account.entity.OperationType;
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
    private final ExternalTransferAdapter transferAdapter;

    private static final BigDecimal ZERO = new BigDecimal(0);

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

    public OperationResponse performOperation(long userId, OperationRequest operationRequest) {
        try {
            Account account = getAccount(userId);
            switch (operationRequest.getType()) {
                case CHECK_BALANCE:
                    return performCheckBalance(account);
                case WITHDRAW:
                    return performWithdraw(operationRequest.getOperationSum(), account);
                case DEPOSIT:
                    return performDeposit(operationRequest.getOperationSum(), account);
                case TRANSFER:
                    return performTransfer(operationRequest, account);
                default:
                    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Operation is not supported");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Operation execution is failed", e);
        }
    }

    private OperationResponse performCheckBalance(Account account) {
        return OperationResponse.builder()
                .type(OperationType.CHECK_BALANCE)
                .currentBalance(account.getBalance())
                .build();
    }

    private OperationResponse performWithdraw(BigDecimal operationSum, Account account) {
        BigDecimal resultBalance = account.getBalance().subtract(operationSum);
        if (resultBalance.compareTo(ZERO) >= 0) {
            resultBalance = resultBalance.setScale(2, RoundingMode.HALF_DOWN);
            account.setBalance(resultBalance);
            repository.save(account);

            return OperationResponse.builder()
                    .type(OperationType.WITHDRAW)
                    .operationSum(operationSum)
                    .currentBalance(account.getBalance())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }
    }

    private OperationResponse performDeposit(BigDecimal operationSum, Account account) {
        BigDecimal resultBalance = account.getBalance().add(operationSum);
        account.setBalance(resultBalance);
        repository.save(account);

        return OperationResponse.builder()
                .type(OperationType.DEPOSIT)
                .operationSum(operationSum)
                .currentBalance(account.getBalance())
                .build();
    }

    private OperationResponse performTransfer(OperationRequest operationRequest, Account account) {
        switch (operationRequest.getServiceType()) {
            case INTERNAL:
                return performInternalTransfer(operationRequest, account);
            case EXTERNAL:
                return performExternalTransfer(operationRequest, account);
            default:
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Service type is not supported");
        }
    }

    private OperationResponse performInternalTransfer(OperationRequest operationRequest, Account account) {
        Account recipient = getAccount(getUserIdFromRequest(operationRequest));
        performWithdraw(operationRequest.getOperationSum(), account);
        performDeposit(operationRequest.getOperationSum(), recipient);
        return OperationResponse.builder()
                .type(operationRequest.getType())
                .operationSum(operationRequest.getOperationSum())
                .currentBalance(account.getBalance())
                .build();
    }

    private long getUserIdFromRequest(OperationRequest operationRequest) {
        try {
            return Long.parseLong(operationRequest.getRecipientId());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
    }

    private OperationResponse performExternalTransfer(OperationRequest request, Account account) {
        BigDecimal operationSum = request.getOperationSum();
        BigDecimal commissionSum = operationSum.multiply(new BigDecimal("0.01"));

        performWithdraw(operationSum.add(commissionSum), account);
        transferAdapter.performExternalTransfer(request.getRecipientId(), operationSum);

        return OperationResponse.builder()
                .type(request.getType())
                .operationSum(operationSum)
                .currentBalance(account.getBalance())
                .build();
    }
}
