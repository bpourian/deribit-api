package com.deribittest.platform.model;

import java.util.Arrays;

public enum ExchangeCurrency {
  ETH("ETH", "Etherium"),
  BTC("BTC", "Bitcoin"),
  USD("USDT", "US Dollar token");

  private final String code;
  private final String description;

  ExchangeCurrency(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static ExchangeCurrency fromCode(String code) {
    return Arrays.stream(ExchangeCurrency.values())
                 .filter(c -> c.getCode().equals(code.toUpperCase()))
                 .findFirst().orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find currency for value %s", code)));
  }
}
