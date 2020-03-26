/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.boundary;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.github.im.bs.business.util.Constants.*;

@RestController
@RequestMapping(path = "/users/{id}/account", produces = APPLICATION_JSON)
@RequiredArgsConstructor
@Api(value = ACCOUNT_HANDLER)
public class AccountResource {
    private final AccountService accountService;

    @GetMapping
    @ApiOperation(value = FIND_ACCOUNT_DESCRIPTION, response = Account.class)
    public ResponseEntity<Account> findAccount(@PathVariable("id") long userId) {
        Account account = accountService.findAccount(userId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(consumes = APPLICATION_JSON)
    @ApiOperation(value = CREATE_ACCOUNT_DESCRIPTION)
    public ResponseEntity<?> createAccount(@PathVariable("id") long userId,
                                           @RequestBody Account account) {
        accountService.createAccount(userId, account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = DELETE_ACCOUNT_DESCRIPTION)
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long userId) {
        accountService.deleteAccount(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/balance")
    @ApiOperation(value = CHECK_BALANCE_DESCRIPTION)
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable("id") long userId) {
        BigDecimal balance = accountService.checkBalance(userId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
