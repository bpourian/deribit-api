package com.deribittest.platform.model.client;

public record AccountBreakdown(String availableFunds,
                               String availableWithdrawalFunds,
                               String balance,
                               String currency,
                               String equity,
                               String initialMargin,
                               String maintenanceMargin,
                               String marginBalance) {
}
