package com.deribittest.platform.persistence;

import com.deribittest.platform.model.Account;
import com.deribittest.platform.model.Accounts;
import java.util.List;

/**
 * Statements used to query or update account
 */
public interface AccountSummeryStatements {

  void updateAccountWithLatestBalance(Accounts accounts);

  List<Account> selectAccountWithEmail(String emailAddress);

}
