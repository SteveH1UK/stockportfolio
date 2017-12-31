package com.steveh1uk.stockportfolio.core.pricing;

import java.math.BigDecimal;

public class PricingResponse {

    private final BigDecimal stockUnitPrice;

    public PricingResponse(BigDecimal stockUnitPrice) {
        this.stockUnitPrice = stockUnitPrice;
    }

    public BigDecimal getStockUnitPrice() {
        return stockUnitPrice;
    }
}
