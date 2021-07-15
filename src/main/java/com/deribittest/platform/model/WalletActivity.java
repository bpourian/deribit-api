package com.deribittest.platform.model;

public record WalletActivity(String address,
                             String amount,
                             String receivedTimestamp,
                             String state,
                             String transactionId,
                             String updatedTimeStamp) {
}
