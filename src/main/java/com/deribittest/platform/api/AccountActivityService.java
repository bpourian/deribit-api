package com.deribittest.platform.api;

import com.deribittest.platform.api.mapper.AccountHolderMapper;
import com.deribittest.platform.client.AccountManagementResource;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.persistence.AccountSummaryPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Scope("singleton")
@RestController
public class AccountActivityService implements AccountActivityResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountActivityService.class);

  private final AccountManagementResource accountManagementResource;
  private final AccountSummaryPersistenceService accountSummaryPersistenceService;
  private final AccountHolderMapper accountHolderMapper;

  @Autowired
  public AccountActivityService(
      AccountManagementResource accountManagementResource,
      AccountSummaryPersistenceService accountSummaryPersistenceService,
      AccountHolderMapper accountHolderMapper) {
    this.accountManagementResource = accountManagementResource;
    this.accountSummaryPersistenceService = accountSummaryPersistenceService;
    this.accountHolderMapper = accountHolderMapper;
  }

  @GetMapping("/account/summaryList/{withPortfolio}")
  @Override
  public Accounts getAndUpdateAccountsSummary(@PathVariable boolean withPortfolio) {
    try {
      LOGGER.info("Calling DERIBIT to fetch latest account information");
      Accounts accounts = accountHolderMapper
          .mapToAccountHolder(accountManagementResource.getSubAccounts(withPortfolio));
      accountSummaryPersistenceService.updateAccountWithLatestBalance(accounts);
      return accounts;
    } catch (Exception e) {
      LOGGER.error("Failed to fetch account details for account - {}", e.getMessage(), e);
      throw new RuntimeException("Failed to fetch account summery");
    }
  }

  @GetMapping("/account/user/{emailAddress}")
  @Override
  public Accounts getAccountsForUser(@PathVariable String emailAddress) {
    LOGGER.info("Handling user request details for user {}", emailAddress);
    return new Accounts(accountSummaryPersistenceService.selectAccountWithEmail(emailAddress));
  }
}
