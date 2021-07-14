package com.deribittest.platform;

import com.deribittest.platform.model.Account;
import com.deribittest.platform.model.AccountType;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.client.AccountBreakdown;
import com.deribittest.platform.model.client.AccountSummary;
import com.deribittest.platform.model.client.AccountSummaryList;
import com.deribittest.platform.model.client.Deposit;
import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.client.Portfolio;
import com.deribittest.platform.model.client.TransferConfirmation;
import com.deribittest.platform.model.client.Withdrawal;
import com.deribittest.platform.model.client.WithdrawalConfirmation;
import com.deribittest.platform.model.client.WithdrawalList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DeribitTestData {

  private static final String balance = "118.72074005";
  private static final String email = "som@som.com";
  private static final int id = 12345;
  private static final boolean isPassword = true;
  private static final boolean loginEnabled = false;
  private static final String availableFunds = "546";
  private static final String availableWithdrawalFunds = "23";
  private static final String currency = "BTC";
  private static final String type = "main";
  private static final String userName = "some_i";
  private static final String address = "address";
  private static final String amount = "address";

  public static AccountSummaryList anAccountSummaryList() {
    return new AccountSummaryList(List.of(anAccountSummary()));
  }

  public static AccountSummary anAccountSummary() {
    return new AccountSummary(
        email,
        id,
        true,
        false,
        aPortfolio(),
        true,
        null,
        true,
        type,
        userName
    );
  }

  public static Portfolio aPortfolio() {
    return new Portfolio(
        Optional.of(anAccountBreakdown("eth")),
        Optional.of(anAccountBreakdown("btc"))
    );
  }

  public static AccountBreakdown anAccountBreakdown(String currency) {
    return new AccountBreakdown(
        availableFunds,
        availableWithdrawalFunds,
        balance,
        currency,
        null,
        null,
        null,
        null
    );
  }

  public static Accounts anAccounts() {
    return new Accounts(List.of(anAccount(ExchangeCurrency.BTC, AccountType.MAIN)));
  }

  public static Account anAccount(ExchangeCurrency exchangeCurrency, AccountType accountType) {
    return new Account(
        id,
        userName,
        email,
        accountType,
        exchangeCurrency,
        new BigDecimal("100"),
        new BigDecimal("100")
    );
  }

  public static DepositList aDepositList() {
    return new DepositList(List.of(aDeposit()));
  }

  public static Deposit aDeposit() {
    return new Deposit(address, amount, null, null, "123", null);
  }

  public static WithdrawalList aWithdrawalList() {
    return new WithdrawalList(List.of(aWithdrawal()));
  }

  public static Withdrawal aWithdrawal() {
    return new Withdrawal(
        address, amount, null, null, currency, null, 123, null, null, "5656", null
    );
  }

  public static TransferConfirmation aTransferConfirmation() {
    return new TransferConfirmation(null, type, null, null, 12, null, "btc", null,
        new BigDecimal("12"));
  }

  public static WithdrawalConfirmation aWithdrawalConfirmation() {
    return new WithdrawalConfirmation(address, amount, null, null, "btc", null, 12, 1, null, 12344,
        12344444);
  }
}
