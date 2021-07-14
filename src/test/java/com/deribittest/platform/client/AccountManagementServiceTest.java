package com.deribittest.platform.client;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deribittest.platform.DeribitTestData;
import com.deribittest.platform.model.client.AccountSummaryList;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountManagementServiceTest {

  @Mock
  private ClientUtilityService clientUtilityService;

  @InjectMocks
  private AccountManagementService underTest;


  @Test
  void shouldGetSubAccounts() {
    // given
    when(clientUtilityService
        .getForObject("private/get_subaccounts", Map.of("with_portfolio", String.valueOf(true)),
            AccountSummaryList.class)).thenReturn(DeribitTestData.anAccountSummaryList());

    // when
    underTest.getSubAccounts(true);

    // then
    verify(clientUtilityService, times(1))
        .getForObject("private/get_subaccounts", Map.of("with_portfolio", String.valueOf(true)),
            AccountSummaryList.class);
  }
}