package com.deribittest.platform.client;

import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.client.TransferConfirmation;
import com.deribittest.platform.model.client.WithdrawalConfirmation;
import com.deribittest.platform.model.client.WithdrawalList;
import java.math.BigDecimal;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * deribit.com waller resource
 *
 * https://docs.deribit.com/?javascript#wallet
 *
 * (not all methods have been implemented)
 */
public interface WalletResource {

  /**
   * Retrieve the latest users withdrawals
   *
   * This defaults to 10 - interface can be changed to accept number of requested items
   *
   * @param exchangeCurrency BTC, ETH ... required
   * @return WithdrawalList
   */
  DepositList getDeposits(ExchangeCurrency exchangeCurrency);

  /**
   * Retrieve the latest users withdrawals
   *
   * * This defaults to 10 - interface can be changed to accept number of requested items
   *
   * @param exchangeCurrency BTC, ETH ... required
   * @return WithdrawalList
   */
  WithdrawalList getWithdrawals(ExchangeCurrency exchangeCurrency);

  /**
   * Transfer funds to sub-account
   *
   * @param exchangeCurrency BTC, ETH, USDT required
   * @param amountToBeTransferred amount required
   * @param subAccountID id required
   * @return TransferConfirmation
   */
  TransferConfirmation submitTransferToSubAccount(ExchangeCurrency exchangeCurrency,
      BigDecimal amountToBeTransferred, Integer subAccountID);

  /**
   * Creates a new withdrawal request
   *
   * @param exchangeCurrency BTC, ETH, USDT - required
   * @param address Address in currency format, it must be in address book - required
   * @param amount Amount of funds to be withdrawn - required
   * @param priority Withdrawal priority, optional for BTC, default: high - optional
   * @param tfa TFA code, required when TFA is enabled for current account - optional
   * @return WithdrawalConfirmation
   */
  WithdrawalConfirmation requestWithdrawal(ExchangeCurrency exchangeCurrency, String address,
      BigDecimal amount, @DefaultValue(value = "high") String priority, String tfa);

}
