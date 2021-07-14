package com.deribittest.platform.model.client;

/**
 * Object based on expected data based on
 *
 * https://docs.deribit.com/?javascript#private-get_subaccounts
 */
public record AccountSummary(
    String email,
    int id,
    boolean isPassword,
    boolean loginEnabled,
    Portfolio portfolio,
    boolean receiveNotifications,
    String systemName,
    boolean tfaEnabled,
    String type,
    String userName
) {

}
