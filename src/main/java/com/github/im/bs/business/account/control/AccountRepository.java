/*
 * 3/21/20, 8:04 PM
 * ivan
 */

package com.github.im.bs.business.account.control;

import com.github.im.bs.business.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("select a from Account a where a.user.id = :userId")
    Account findAccountByUserId(long userId);

    @Query("select a from Account a where a.createdAt >= :startDate and a.createdAt < :endDate order by a.user.id")
    List<Account> findCreatedAccountsByPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
