package com.deribittest.platform.api.mapper;

import com.deribittest.platform.model.WalletActivity;
import com.deribittest.platform.model.WalletActivityList;
import com.deribittest.platform.model.client.DepositList;
import com.deribittest.platform.model.client.WithdrawalList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WalletActivityMapper {

  public WalletActivityList mapToWalletActivityList(WithdrawalList withdrawalList,
      DepositList depositList) {

    List<WalletActivity> walletActivityList = new ArrayList<>();

    Optional.ofNullable(withdrawalList).map(WithdrawalList::data).orElse(Collections.emptyList())
            .forEach(i -> walletActivityList.add(
                new WalletActivity(i.address(), i.amount(), i.createdTimestamp(), i.state(),
                    i.transactionId(), i.updatedTimeStamp())));

    Optional.ofNullable(depositList).map(DepositList::data).orElse(Collections.emptyList())
            .forEach(i -> walletActivityList.add(
                new WalletActivity(i.address(), i.amount(), i.receivedTimestamp(), i.state(),
                    i.transactionId(), i.updatedTimeStamp())));

    return new WalletActivityList(walletActivityList);
  }
}
