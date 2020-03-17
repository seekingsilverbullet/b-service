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

@RestController
@RequestMapping(path = "/users/{id}/account", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "Account Handler")
public class AccountResource {
    private final AccountService service;

    @GetMapping
    @ApiOperation(value = "Returns existing account by user id", response = Account.class)
    public ResponseEntity<Account> getAccount(@PathVariable("id") long userId) {
        Account account = service.getAccount(userId);
        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates new user account")
    public ResponseEntity<?> create(@PathVariable("id") long userId,
                                    @RequestBody Account account) {
        return service.create(userId, account)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes the existing user account")
    public ResponseEntity<?> delete(@PathVariable("id") long userId) {
        return service.delete(userId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
