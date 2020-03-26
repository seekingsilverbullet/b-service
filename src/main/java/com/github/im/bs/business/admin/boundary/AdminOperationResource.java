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

import static com.github.im.bs.business.util.Constants.*;

@RestController
@RequestMapping(path = "/admin", produces = APPLICATION_JSON)
@RequiredArgsConstructor
@Api(value = ADMIN_OPERATION_HANDLER)
public class AdminOperationResource {
    private final AdminOperationService adminOperationService;

    @GetMapping(path = "/accounts")
    @ApiOperation(value = ALL_ACCOUNTS_DESCRIPTION, response = Account.class, responseContainer = LIST)
    public ResponseEntity<List<Account>> findAllAccounts() {
        return new ResponseEntity<>(adminOperationService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/transactions")
    @ApiOperation(value = ALL_TRANSACTIONS_DESCRIPTION, response = Transaction.class, responseContainer = LIST)
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        return new ResponseEntity<>(adminOperationService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping(path = "/report")
    @ApiOperation(value = MONTH_REPORT_DESCRIPTION, response = Report.class)
    public ResponseEntity<Report> createMonthReport(@RequestParam Month month) {
        return new ResponseEntity<>(adminOperationService.createMonthReport(month), HttpStatus.OK);
    }

    @PostMapping(path = "/commission")
    @ApiOperation(value = COMMISSION_CHARGING_DESCRIPTION)
    public ResponseEntity<?> performCommissionCharging() {
        adminOperationService.performCommissionCharging();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
