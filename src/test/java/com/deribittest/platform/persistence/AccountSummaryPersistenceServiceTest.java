package com.deribittest.platform.persistence;

import static com.deribittest.platform.DeribitTestData.anAccounts;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class AccountSummaryPersistenceServiceTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private AccountSummaryPersistenceService underTest;

  @Test
  void shouldUpdateAccountWithLatestBalance() {
    underTest.updateAccountWithLatestBalance(anAccounts());
    verify(jdbcTemplate, times(1))
        .update(anyString(), anyInt(), anyString(), anyString(), anyString());
    verify(jdbcTemplate, times(1))
        .update(anyString(), anyInt(), anyString(), any(), any(), any(), any());
  }

  @Test
  void shouldSelectAccountWithEmail() {
    underTest.selectAccountWithEmail("email@g.com");
    verify(jdbcTemplate, times(1)).queryForList(anyString());
  }
}