/*
 * 3/23/20, 7:40 PM
 * ivan
 */

package com.github.im.bs.business.transaction.control;

import com.github.im.bs.business.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
