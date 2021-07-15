package com.deribittest.platform.api;

import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.WalletActivityList;

public interface AccountActivityResource {

  /**
   * Balance summary of all accounts
   *
   * @param withPortfolio boolean
   */
  Accounts getAndUpdateAccountsSummary(boolean withPortfolio);


  /**
   * Fetch account data for user from db
   *
   * @param emailAddress unique user email address
   * @return Accounts
   */
  Accounts getAccountsForUser(String emailAddress);


  /**
   * Gets the history of withdrawals and deposits
   *
   * @return WalletActivityList
   */
  WalletActivityList getWalletActivityList();

}
