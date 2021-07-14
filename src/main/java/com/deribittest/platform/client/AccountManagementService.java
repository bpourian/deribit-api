package com.deribittest.platform.client;

import com.deribittest.platform.model.client.AccountSummaryList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class AccountManagementService implements AccountManagementResource {

  private static final String GET_SUBACCOUNTS = "private/get_subaccounts";

  private final ClientUtilityService clientUtilityService;

  @Autowired
  public AccountManagementService(
      ClientUtilityService clientUtilityService) {
    this.clientUtilityService = clientUtilityService;
  }

  @Override
  public AccountSummaryList getSubAccounts(boolean withPortfolio) {
    return clientUtilityService
        .getForObject(GET_SUBACCOUNTS, Map.of("with_portfolio", String.valueOf(withPortfolio)),
            AccountSummaryList.class);
  }
}
