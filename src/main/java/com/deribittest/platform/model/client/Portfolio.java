package com.deribittest.platform.model.client;

import java.util.Optional;

public record Portfolio(Optional<AccountBreakdown> eth, Optional<AccountBreakdown> btc) {}
