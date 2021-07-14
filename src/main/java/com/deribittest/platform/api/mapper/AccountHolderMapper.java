package com.deribittest.platform.api.mapper;

import com.deribittest.platform.model.Account;
import com.deribittest.platform.model.AccountType;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.client.AccountBreakdown;
import com.deribittest.platform.model.client.AccountSummaryList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderMapper {

  public Accounts mapToAccountHolder(AccountSummaryList accounts) {
    List<Account> accountList = new ArrayList<>();

    accounts.result().forEach(acc -> {
      if (acc.portfolio().btc().isPresent()) {
        AccountBreakdown btc = acc.portfolio().btc().get();
        Account btcAccount = Account.newBuilder()
                                    .withAccountId(acc.id())
                                    .withUserName(acc.userName())
                                    .withEmailAddress(acc.email())
                                    .withAccountType(
                                        AccountType.fromCode(acc.type()))
                                    .withExchangeCurrency(ExchangeCurrency.BTC)
                                    .withCurrentBalance(btc.balance() == null ? BigDecimal.ZERO : new BigDecimal(btc.balance()))
                                    // need more clarity on balances
                                    .withReservedBalance(btc.marginBalance() == null ? BigDecimal.ZERO : new BigDecimal(btc.marginBalance()))
                                    .build();
        accountList.add(btcAccount);
      }

      if (acc.portfolio().eth().isPresent()) {
        AccountBreakdown eth = acc.portfolio().eth().get();
        Account ethAccount = Account.newBuilder()
                                    .withAccountId(acc.id())
                                    .withUserName(acc.userName())
                                    .withEmailAddress(acc.email())
                                    .withAccountType(
                                        AccountType.fromCode(acc.type()))
                                    .withExchangeCurrency(ExchangeCurrency.ETH)
                                    .withCurrentBalance(eth.balance() == null ? BigDecimal.ZERO : new BigDecimal(eth.balance()))
                                    // need more clarity on balances
                                    .withReservedBalance(eth.marginBalance() == null ? BigDecimal.ZERO : new BigDecimal(eth.marginBalance()))
                                    .build();
        accountList.add(ethAccount);
      }
    });
    return new Accounts(accountList);
  }
}
