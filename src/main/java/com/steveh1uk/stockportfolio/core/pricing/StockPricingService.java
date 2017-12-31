package com.steveh1uk.stockportfolio.core.pricing;

public interface StockPricingService {

    PricingResponse findPriceForStock(PricingRequest pricingRequest);
}
