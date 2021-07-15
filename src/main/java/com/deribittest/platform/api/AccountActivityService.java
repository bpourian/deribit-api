package com.deribittest.platform.api;

import com.deribittest.platform.api.mapper.AccountHolderMapper;
import com.deribittest.platform.api.mapper.WalletActivityMapper;
import com.deribittest.platform.client.AccountManagementResource;
import com.deribittest.platform.client.WalletResource;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.WalletActivityList;
import com.deribittest.platform.model.client.Deposit;
import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.client.Withdrawal;
import com.deribittest.platform.model.client.WithdrawalList;
import com.deribittest.platform.persistence.AccountSummaryPersistenceService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  private final WalletResource walletResource;
  private final AccountSummaryPersistenceService accountSummaryPersistenceService;
  private final AccountHolderMapper accountHolderMapper;
  private final WalletActivityMapper walletActivityMapper;

  @Autowired
  public AccountActivityService(
      AccountManagementResource accountManagementResource,
      WalletResource walletResource,
      AccountSummaryPersistenceService accountSummaryPersistenceService,
      AccountHolderMapper accountHolderMapper,
      WalletActivityMapper walletActivityMapper) {
    this.accountManagementResource = accountManagementResource;
    this.walletResource = walletResource;
    this.accountSummaryPersistenceService = accountSummaryPersistenceService;
    this.accountHolderMapper = accountHolderMapper;
    this.walletActivityMapper = walletActivityMapper;
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

  @GetMapping("/account/wallet/activity")
  @Override
  public WalletActivityList getWalletActivityList() {
    LOGGER.info("Fetching wallet activity details");
    Optional<DepositList> depositsBtc;
    Optional<DepositList> depositsEth;
    Optional<WithdrawalList> withdrawalBtc;
    Optional<WithdrawalList> withdrawalEth;
    try {
      // we may need a call for USDT here too
      depositsBtc = Optional.ofNullable(walletResource.getDeposits(ExchangeCurrency.BTC));
      depositsEth = Optional.ofNullable(walletResource.getDeposits(ExchangeCurrency.ETH));

      withdrawalBtc = Optional.ofNullable(walletResource.getWithdrawals(ExchangeCurrency.BTC));
      withdrawalEth = Optional.ofNullable(walletResource.getWithdrawals(ExchangeCurrency.ETH));
    } catch (Exception e) {
      LOGGER.error("Failed to fetch wallet activity details with error", e);
      throw new RuntimeException("Failed to fetch wallet activity");
    }

    List<Deposit> depositList = new ArrayList<>();
    List<Withdrawal> withdrawalList = new ArrayList<>();

    depositList.addAll(depositsBtc.map(DepositList::data).orElse(Collections.emptyList()));
    depositList.addAll(depositsEth.map(DepositList::data).orElse(Collections.emptyList()));
    withdrawalList.addAll(withdrawalBtc.map(WithdrawalList::data).orElse(Collections.emptyList()));
    withdrawalList.addAll(withdrawalEth.map(WithdrawalList::data).orElse(Collections.emptyList()));

    return walletActivityMapper
        .mapToWalletActivityList(new WithdrawalList(withdrawalList), new DepositList(depositList));
  }
}
