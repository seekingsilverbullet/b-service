/*
 * 3/21/20, 10:21 PM
 * ivan
 */

package com.github.im.bs.business.admin.boundary;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.admin.control.AdminOperationService;
import com.github.im.bs.business.admin.entity.Report;
import com.github.im.bs.business.transaction.entity.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "Admin Operation Handler")
public class AdminOperationResource {
    private final AdminOperationService adminOperationService;

    @GetMapping(path = "/accounts")
    @ApiOperation(value = "Returns all of existing accounts", response = Account.class, responseContainer = "List")
    public ResponseEntity<List<Account>> findAllAccounts() {
        return new ResponseEntity<>(adminOperationService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/transactions")
    @ApiOperation(value = "Returns all of existing transactions", response = Transaction.class, responseContainer = "List")
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        return new ResponseEntity<>(adminOperationService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping(path = "/report")
    @ApiOperation(value = "Creates admin month report", response = Report.class)
    public ResponseEntity<Report> createMonthReport(@RequestParam Month month) {
        return new ResponseEntity<>(adminOperationService.createMonthReport(month), HttpStatus.OK);
    }

    @PostMapping(path = "/commission")
    @ApiOperation(value = "Perform commission charging")
    public ResponseEntity<?> performCommissionCharging() {
        adminOperationService.performCommissionCharging();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
