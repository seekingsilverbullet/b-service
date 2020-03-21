/*
 * 3/17/20, 11:54 PM
 * ivan
 */

package com.github.im.bs.business.account.boundary;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.account.entity.Operation;
import com.github.im.bs.business.account.entity.OperationResponse;
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
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates new user account")
    public ResponseEntity<?> create(@PathVariable("id") long userId,
                                    @RequestBody Account account) {
        service.create(userId, account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes the existing user account")
    public ResponseEntity<?> delete(@PathVariable("id") long userId) {
        service.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/operation")
    @ApiOperation(value = "Performs the user account operation")
    public ResponseEntity<OperationResponse> performOperation(@PathVariable("id") long userId,
                                                              @RequestBody Operation operation) {
        OperationResponse response = service.performOperation(userId, operation);
        return response.isPerformed()
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(response, HttpStatus.NOT_MODIFIED);
    }
}
