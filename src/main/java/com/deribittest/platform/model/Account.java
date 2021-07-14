package com.deribittest.platform.model;

import java.math.BigDecimal;

public record Account(Integer accountId,
                      String userName,
                      String emailAddress,
                      AccountType accountType,
                      ExchangeCurrency exchangeCurrency,
                      BigDecimal currentBalance,
                      BigDecimal reservedBalance) {

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private Integer accountId;
    private String userName;
    private String emailAddress;
    private AccountType accountType;
    private ExchangeCurrency exchangeCurrency;
    private BigDecimal currentBalance;
    private BigDecimal reservedBalance;

    public Builder withAccountId(Integer accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder withUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder withEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    public Builder withAccountType(AccountType accountType) {
      this.accountType = accountType;
      return this;
    }

    public Builder withExchangeCurrency(ExchangeCurrency exchangeCurrency) {
      this.exchangeCurrency = exchangeCurrency;
      return this;
    }

    public Builder withCurrentBalance(BigDecimal currentBalance) {
      this.currentBalance = currentBalance;
      return this;
    }

    public Builder withReservedBalance(BigDecimal reservedBalance) {
      this.reservedBalance = reservedBalance;
      return this;
    }

    public Account build() {
      return new Account(
          accountId,
          userName,
          emailAddress,
          accountType,
          exchangeCurrency,
          currentBalance,
          reservedBalance);
    }
  }
}
