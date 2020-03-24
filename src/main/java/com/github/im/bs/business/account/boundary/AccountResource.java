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

@RestController
@RequestMapping(path = "/users/{id}/account", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "Account Handler")
public class AccountResource {
    private final AccountService accountService;

    @GetMapping
    @ApiOperation(value = "Returns existing account by user id", response = Account.class)
    public ResponseEntity<Account> findAccount(@PathVariable("id") long userId) {
        Account account = accountService.findAccount(userId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates new user account")
    public ResponseEntity<?> createAccount(@PathVariable("id") long userId,
                                           @RequestBody Account account) {
        accountService.createAccount(userId, account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes the existing user account")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long userId) {
        accountService.deleteAccount(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/balance")
    @ApiOperation(value = "Returns user account balance")
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable("id") long userId) {
        BigDecimal balance = accountService.checkBalance(userId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
