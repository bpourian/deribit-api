package com.deribittest.platform.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deribittest.platform.DeribitTestData;
import com.deribittest.platform.api.mapper.AccountHolderMapper;
import com.deribittest.platform.client.AccountManagementResource;
import com.deribittest.platform.model.AccountType;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.ExchangeCurrency;
import com.deribittest.platform.persistence.AccountSummaryPersistenceService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountActivityServiceTest {

  @Mock
  private AccountManagementResource accountManagementResource;

  @Mock
  private AccountSummaryPersistenceService accountSummaryPersistenceService;

  @Mock
  private AccountHolderMapper accountHolderMapper;

  @InjectMocks
  private AccountActivityService underTest;

  @Test
  void shouldGetAndUpdateAccountsSummary() {
    // given
    when(accountManagementResource.getSubAccounts(anyBoolean()))
        .thenReturn(DeribitTestData.anAccountSummaryList());
    when(accountHolderMapper.mapToAccountHolder(DeribitTestData.anAccountSummaryList()))
        .thenReturn(DeribitTestData.anAccounts());

    // when
    underTest.getAndUpdateAccountsSummary(true);

    // then
    verify(accountManagementResource, only()).getSubAccounts(true);
    verify(accountSummaryPersistenceService, only()).updateAccountWithLatestBalance(any());
  }

  @Test
  void shouldGetAccountsForUser() {
    // given
    String email = "random";
    when(accountSummaryPersistenceService.selectAccountWithEmail(email))
        .thenReturn(List.of(DeribitTestData.anAccount(ExchangeCurrency.BTC, AccountType.MAIN)));

    // when
    Accounts actual = underTest.getAccountsForUser(email);

    // then
    verify(accountSummaryPersistenceService, only()).selectAccountWithEmail(email);
  }
}