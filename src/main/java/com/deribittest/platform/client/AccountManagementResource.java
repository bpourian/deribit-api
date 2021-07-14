package com.deribittest.platform.client;


import com.deribittest.platform.model.client.AccountSummaryList;

/**
 * deribit.com account management resource
 *
 * https://docs.deribit.com/?javascript#account-management
 *
 * (not all methods have been implemented)
 */
public interface AccountManagementResource {

  /**
   * Lists all the accounts associated with the account requesting it
   *
   * (includes main and sub-account types)
   *
   * @param withPortfolio true/false optional
   * @return AccountSummaryList
   */
  AccountSummaryList getSubAccounts(boolean withPortfolio);

}
