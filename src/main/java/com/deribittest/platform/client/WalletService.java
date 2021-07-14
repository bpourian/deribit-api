package com.deribittest.platform.client;

import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.client.TransferConfirmation;
import com.deribittest.platform.model.client.WithdrawalConfirmation;
import com.deribittest.platform.model.client.WithdrawalList;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class WalletService implements WalletResource {

  private static final String GET_DEPOSITS = "/private/get_deposits";
  private static final String GET_WITHDRAWALS = "/private/get_withdrawals";
  private static final String SUBMIT_TRANSFER_TO_SUBACCOUNT = "/private/submit_transfer_to_subaccount";
  private static final String WITHDRAW = "/private/withdraw";

  private final ClientUtilityService clientUtilityService;

  @Autowired
  public WalletService(ClientUtilityService clientUtilityService) {
    this.clientUtilityService = clientUtilityService;
  }

  @Override
  public DepositList getDeposits(ExchangeCurrency exchangeCurrency) {
    return clientUtilityService
        .getForObject(GET_DEPOSITS, Map.of("currency", exchangeCurrency.getCode()),
            DepositList.class);
  }

  @Override
  public WithdrawalList getWithdrawals(ExchangeCurrency exchangeCurrency) {
    return clientUtilityService
        .getForObject(GET_WITHDRAWALS, Map.of("currency", exchangeCurrency.getCode()),
            WithdrawalList.class);
  }

  @Override
  public TransferConfirmation submitTransferToSubAccount(ExchangeCurrency exchangeCurrency,
      BigDecimal amountToBeTransferred, Integer subAccountID) {
    Map<String, String> params = Map
        .of("currency", exchangeCurrency.getCode(),
            "amount", amountToBeTransferred.toString(),
            "destination", Integer.toString(subAccountID)
        );

    return clientUtilityService
        .getForObject(SUBMIT_TRANSFER_TO_SUBACCOUNT, params, TransferConfirmation.class);
  }

  @Override
  public WithdrawalConfirmation requestWithdrawal(ExchangeCurrency exchangeCurrency, String address,
      BigDecimal amount, String priority, String tfa) {
    Map<String, String> params = Map
        .of("currency", exchangeCurrency.getCode(),
            "address", address,
            "amount", amount.toString(),
            "priority", priority,
            "tfa", tfa
        );
    return clientUtilityService.getForObject(WITHDRAW, params, WithdrawalConfirmation.class);
  }
}
