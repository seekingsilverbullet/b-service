/*
 * 3/24/20, 3:49 PM
 * ivan
 */

package com.github.im.bs.business.transaction.boundary;

import com.github.im.bs.business.transaction.entity.TransactionRequest;
import com.github.im.bs.business.transaction.entity.TransactionResponse;
import com.github.im.bs.business.transaction.control.TransactionService;
import com.github.im.bs.business.transaction.entity.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.im.bs.business.util.Constants.*;

@RestController
@RequestMapping(path = "/users/{id}/account/transactions", produces = APPLICATION_JSON)
@RequiredArgsConstructor
@Api(value = TRANSACTION_HANDLER)
public class TransactionResource {
    private final TransactionService transactionService;

    @GetMapping
    @ApiOperation(value = ALL_USER_TRANSACTIONS_DESCRIPTION, response = Transaction.class, responseContainer = LIST)
    public ResponseEntity<List<Transaction>> findTransactions(@PathVariable("id") long userId) {
        List<Transaction> transactions = transactionService.findUserTransactions(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping(consumes = APPLICATION_JSON)
    @ApiOperation(value = PERFORM_TRANSACTION_DESCRIPTION)
    public ResponseEntity<TransactionResponse> performTransaction(@PathVariable("id") long userId,
                                                                  @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.performTransaction(userId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
