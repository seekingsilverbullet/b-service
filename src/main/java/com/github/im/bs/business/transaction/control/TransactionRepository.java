/*
 * 3/23/20, 7:40 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import com.github.im.bs.business.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.user.id = :userId")
    List<Transaction> findTransactionsByUserId(long userId);
}
