/*
 * 3/21/20, 10:21 PM
 * ivan
 */

package com.github.im.bs.business.admin.boundary;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.transaction.control.TransactionService;
import com.github.im.bs.business.transaction.entity.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "Admin Operation Handler")
public class AdminOperationResource {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping(path = "/accounts")
    @ApiOperation(value = "Returns all of existing accounts", response = Account.class, responseContainer = "List")
    public ResponseEntity<List<Account>> findAllAccounts() {
        return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/transactions")
    @ApiOperation(value = "Returns all of existing transactions", response = Transaction.class, responseContainer = "List")
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        return new ResponseEntity<>(transactionService.findAllTransactions(), HttpStatus.OK);
    }
}
