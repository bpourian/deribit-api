package com.deribittest.platform.model.client;

public record Withdrawal(
    String address,
    String amount,
    String confirmedTimestamp,
    String createdTimestamp,
    String currency,
    String fee,
    int id,
    String priority,
    String state,
    String transactionId,
    String updatedTimeStamp
) {

}
