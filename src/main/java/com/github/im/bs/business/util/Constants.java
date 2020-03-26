/*
 * 3/26/20, 8:06 PM
 * ivan
 */

package com.github.im.bs.business.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    //common
    public static final String APPLICATION_JSON = "application/json";
    public static final String LIST = "List";
    public static final String COMMISSION = "commission";

    // swagger api
    public static final String USER = "User";
    public static final String USER_TYPE = "User type";
    public static final String USER_HANDLER = "User Handler";
    public static final String ALL_USERS_DESCRIPTION = "Returns all of existing users";
    public static final String FIND_USER_DESCRIPTION = "Returns existing user by id";
    public static final String CREATE_USER_DESCRIPTION = "Creates new user";
    public static final String UPDATE_USER_DESCRIPTION = "Updates the existing user by id";
    public static final String DELETE_USER_DESCRIPTION = "Deletes the existing user by id";
    public static final String TRANSACTION = "Transaction";
    public static final String TRANSACTION_REQUEST = "Transaction request";
    public static final String TRANSACTION_RESPONSE = "Transaction response";
    public static final String TRANSACTION_TYPE = "Transaction type";
    public static final String TRANSACTION_HANDLER = "Transaction Handler";
    public static final String ALL_USER_TRANSACTIONS_DESCRIPTION = "Returns all existing user account transactions by user id";
    public static final String PERFORM_TRANSACTION_DESCRIPTION = "Performs user account transactions";
    public static final String REPORT = "Report";
    public static final String ADMIN_OPERATION_HANDLER = "Admin Operation Handler";
    public static final String ALL_ACCOUNTS_DESCRIPTION = "Returns all of existing accounts";
    public static final String ALL_TRANSACTIONS_DESCRIPTION = "Returns all of existing transactions";
    public static final String MONTH_REPORT_DESCRIPTION = "Creates admin month report";
    public static final String COMMISSION_CHARGING_DESCRIPTION = "Perform commission charging";
    public static final String ACCOUNT = "Account";
    public static final String ACCOUNT_HANDLER = "Account Handler";
    public static final String FIND_ACCOUNT_DESCRIPTION = "Returns existing account by user id";
    public static final String CREATE_ACCOUNT_DESCRIPTION = "Creates new user account";
    public static final String DELETE_ACCOUNT_DESCRIPTION = "Deletes the existing user account";
    public static final String CHECK_BALANCE_DESCRIPTION = "Returns user account balance";

    //exception
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String UNSUPPORTED_TRANSACTION_MESSAGE = "Transaction type is not supported";
    public static final String FAILED_TRANSACTION_MESSAGE = "Transaction execution is failed";
    public static final String NOT_ENOUGH_MONEY = "Not enough money";
    public static final String INVALID_USER_ID = "Invalid user id";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ACCOUNT_EXISTS = "Account exists";
    public static final String ACCOUNT_NOT_EXISTS = "Account doesn't exist";

    //logger
    public static final String USERS_RETRIEVED_MESSAGE = "All users have retrieved. Amount: {}";
    public static final String USER_RETRIEVED_MESSAGE = "The user has retrieved by id: {}";
    public static final String USER_CREATED_MESSAGE = "The user has created: {}";
    public static final String USER_UPDATED_MESSAGE = "The user has updated: {}";
    public static final String USER_DELETED_MESSAGE = "The user has deleted: {}";
    public static final String ALL_USERS_BY_PERIOD_MESSAGE = "Retrieved all users created from {} to {}";
    public static final String TRANSACTIONS_RETRIEVED_MESSAGE = "All transactions have retrieved. Amount: {}";
    public static final String USER_TRANSACTIONS_RETRIEVED_MESSAGE = "All user '{}' transactions have retrieved. Amount: {}";
    public static final String TRANSACTION_STARTED_MESSAGE = "Trying to perform transaction for user '{}': {}";
    public static final String TRANSACTION_REGISTERED_MESSAGE = "The transaction has registered: {}";
    public static final String EXTERNAL_TRANSACTION_PERFORMED_MESSAGE = "Performed external transfer request: '{}' -> {}";
    public static final String ALL_TRANSACTIONS_BY_PERIOD_MESSAGE = "Retrieved all transactions created from {} to {}";
    public static final String ACCOUNTS_RETRIEVED_MESSAGE = "All accounts have retrieved. Amount: {}";
    public static final String ACCOUNT_RETRIEVED_MESSAGE = "The account has retrieved by user id: {}";
    public static final String ACCOUNT_CREATED_MESSAGE = "The account has created: {}";
    public static final String ACCOUNT_UPDATED_MESSAGE = "The account has updated: {}";
    public static final String ACCOUNT_DELETED_MESSAGE = "The account has deleted: {}";
    public static final String ACCOUNT_BALANCE_RETRIEVED_MESSAGE = "Retrieved account balance for user '{}': {}";
    public static final String ALL_ACCOUNTS_BY_PERIOD_MESSAGE = "Retrieved all accounts created from {} to {}";
    public static final String COMMISSION_CHARGING_STARTED_MESSAGE = "Scheduled commission charging started at {}";
    public static final String COMMISSION_CHARGING_FINISHED_MESSAGE = "Scheduled commission charging finished at {}";
    public static final String COMMISSION_CHARGED_MESSAGE = "The commission '{}' charged from {}";
}
