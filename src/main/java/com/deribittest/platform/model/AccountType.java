package com.deribittest.platform.model;

import java.util.Arrays;

public enum AccountType {
  MAIN("MAIN", "main account"),
  SUB_ACCOUNT("SUBACCOUNT", "sub-account");

  private final String code;
  private final String description;

  AccountType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static AccountType fromCode(String code) {
    return Arrays.stream(AccountType.values()).filter(c -> c.getCode().equals(code.toUpperCase()))
                 .findFirst().orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find account type for value %s", code)));
  }
}
