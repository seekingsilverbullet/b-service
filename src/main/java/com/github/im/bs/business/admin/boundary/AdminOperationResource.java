/*
 * 3/21/20, 10:21 PM
 * ivan
 */

package com.github.im.bs.business.admin.boundary;

import com.github.im.bs.business.account.control.AccountService;
import com.github.im.bs.business.account.entity.Account;
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

    @GetMapping(path = "/accounts")
    @ApiOperation(value = "Returns all of existing accounts", response = Account.class, responseContainer = "List")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
}
