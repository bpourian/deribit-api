package com.deribittest.platform.persistence;

import com.deribittest.platform.model.Account;
import com.deribittest.platform.model.AccountType;
import com.deribittest.platform.model.Accounts;
import com.deribittest.platform.model.ExchangeCurrency;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Scope("singleton")
@Component
public class AccountSummaryPersistenceService implements AccountSummeryStatements {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public AccountSummaryPersistenceService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  @Override
  public void updateAccountWithLatestBalance(Accounts accounts) {
    accounts.accounts().forEach(account -> {
      jdbcTemplate.update("""
              INSERT INTO public.account_holder (account_id, type, user_name, email_address) VALUES (?, ?, ?, ?)
              ON CONFLICT ON CONSTRAINT pk_account_holder DO NOTHING 
              """, account.accountId(), account.accountType().getCode(), account.userName(),
          account.emailAddress());

      jdbcTemplate.update("""
              INSERT INTO public.portfolio (account_id, currency, current_balance, reserved_funds) VALUES (?, ?, ?, ?)
              ON CONFLICT ON CONSTRAINT pk_portfolio DO UPDATE SET current_balance = ?, reserved_funds = ?
              """, account.accountId(), account.exchangeCurrency().getCode(), account.currentBalance(),
          account.reservedBalance(), account.currentBalance(), account.reservedBalance());
    });
  }

  @Override
  public List<Account> selectAccountWithEmail(String emailAddress) {
    List<Map<String, Object>> data = jdbcTemplate.queryForList("""
        SELECT ah.user_name,
               ah.email_address,
               ah.type,
               pf.account_id,
               pf.currency,
               pf.current_balance,
               pf.reserved_funds
        FROM public.account_holder ah
        	     JOIN public.portfolio pf ON ah.account_id = pf.account_id
        WHERE ah.email_address =  
        """ + String.format("'%s'", emailAddress));

    return data.stream().map(this::mapToAccount).collect(Collectors.toList());
  }

  private Account mapToAccount(Map<String, Object> record) {
    return Account.newBuilder()
                  .withAccountId((int) record.get("account_id"))
                  .withUserName((String) record.get("user_name"))
                  .withEmailAddress((String) record.get("email_address"))
                  .withExchangeCurrency(ExchangeCurrency.fromCode((String) record.get("currency")))
                  .withAccountType(AccountType.fromCode((String) record.get("type")))
                  .withCurrentBalance(new BigDecimal(String.valueOf(record.get("reserved_funds"))))
                  .withReservedBalance(
                      new BigDecimal(String.valueOf(record.get("current_balance"))))
                  .build();
  }
}
