package com.deribittest.platform.model.client;

import java.math.BigDecimal;

public record TransferConfirmation(String updatedTimestamp,
                                   String type,
                                   String state,
                                   String otherSide,
                                   Integer id,
                                   String direction,
                                   String currency,
                                   String createdTimestamp,
                                   BigDecimal amount) {

}
