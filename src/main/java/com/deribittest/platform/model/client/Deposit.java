package com.deribittest.platform.model.client;

public record Deposit(String address,
                      String amount,
                      String receivedTimestamp,
                      String state,
                      String transactionId,
                      String updatedTimeStamp) {

}
