package com.deribittest.platform.client;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deribittest.platform.DeribitTestData;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.client.TransferConfirmation;
import com.deribittest.platform.model.client.WithdrawalConfirmation;
import com.deribittest.platform.model.client.WithdrawalList;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

  @Mock
  private ClientUtilityService clientUtilityService;

  @InjectMocks
  private WalletService underTest;

  @Test
  void shouldGetDeposits() {
    // given
    when(clientUtilityService
        .getForObject("/private/get_deposits", Map.of("currency", ExchangeCurrency.BTC.getCode()),
            DepositList.class)).thenReturn(DeribitTestData.aDepositList());

    // when
    underTest.getDeposits(ExchangeCurrency.BTC);

    // then
    verify(clientUtilityService, times(1))
        .getForObject("/private/get_deposits", Map.of("currency", ExchangeCurrency.BTC.getCode()),
            DepositList.class);
  }

  @Test
  void shouldGetWithdrawals() {
    // given
    when(clientUtilityService.getForObject("/private/get_withdrawals",
        Map.of("currency", ExchangeCurrency.BTC.getCode()), WithdrawalList.class))
        .thenReturn(DeribitTestData.aWithdrawalList());

    // when
    underTest.getWithdrawals(ExchangeCurrency.BTC);

    // then
    verify(clientUtilityService, times(1)).getForObject("/private/get_withdrawals",
        Map.of("currency", ExchangeCurrency.BTC.getCode()), WithdrawalList.class);
  }

  @Test
  void shouldSubmitTransferToSubAccount() {
    // given
    when(clientUtilityService.getForObject("/private/submit_transfer_to_subaccount",
        Map.of("currency", ExchangeCurrency.BTC.getCode(),
            "amount", new BigDecimal("10").toString(),
            "destination", Integer.toString(123)), TransferConfirmation.class))
        .thenReturn(DeribitTestData.aTransferConfirmation());

    // when
    underTest.submitTransferToSubAccount(ExchangeCurrency.BTC, new BigDecimal("10"), 123);

    // then
    verify(clientUtilityService, times(1)).getForObject("/private/submit_transfer_to_subaccount",
        Map.of("currency", ExchangeCurrency.BTC.getCode(),
            "amount", new BigDecimal("10").toString(),
            "destination", Integer.toString(123)), TransferConfirmation.class);
  }

  @Test
  void shouldRequestWithdrawal() {
    // given
    when(clientUtilityService.getForObject("/private/withdraw",
        Map.of("currency", ExchangeCurrency.BTC.getCode(),
            "address", "address",
            "amount", "10",
            "priority", "1",
            "tfa", "true"), WithdrawalConfirmation.class))
        .thenReturn(DeribitTestData.aWithdrawalConfirmation());

    // when
    underTest.requestWithdrawal(ExchangeCurrency.BTC, "address", new BigDecimal("10"), "1", "true");

    // then
    verify(clientUtilityService, times(1)).getForObject("/private/withdraw",
        Map.of("currency", ExchangeCurrency.BTC.getCode(),
            "address", "address",
            "amount", "10",
            "priority", "1",
            "tfa", "true"), WithdrawalConfirmation.class);
  }
}
