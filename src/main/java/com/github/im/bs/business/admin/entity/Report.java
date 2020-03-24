/*
 * 3/24/20, 8:02 PM
 * ivan
 */

package com.github.im.bs.business.admin.entity;

import com.github.im.bs.business.account.entity.Account;
import com.github.im.bs.business.transaction.entity.Transaction;
import com.github.im.bs.business.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Report")
public class Report {
    private List<User> usersCreated;
    private List<Account> accountsCreated;
    private List<Transaction> transactionsPerformed;
}
