package com.deribittest.platform.model.client;

public record WithdrawalConfirmation(String address,
                                     String amount,
                                     String confirmed_timestamp,
                                     String created_timestamp,
                                     String currency,
                                     String fee,
                                     int id,
                                     int priority,
                                     String state,
                                     int transactionId,
                                     int updatedTimestamp) {

}
