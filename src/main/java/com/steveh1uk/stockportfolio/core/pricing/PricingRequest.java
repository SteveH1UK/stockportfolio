package com.steveh1uk.stockportfolio.core.pricing;

public class PricingRequest {

    private final String stockCode;


    public PricingRequest(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCode() {
        return stockCode;
    }
}
