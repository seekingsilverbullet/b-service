/*
 * 3/23/20, 7:39 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository repository;

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        log.info("All transactions have retrieved. Amount: {}", transactions.size());
        return new ArrayList<>(transactions);
    }

    public void register(Transaction transaction) {
        transaction.setExecutionTime(LocalDateTime.now());
        repository.save(transaction);
        log.info("The transaction has registered: {}", transaction);
    }
}
