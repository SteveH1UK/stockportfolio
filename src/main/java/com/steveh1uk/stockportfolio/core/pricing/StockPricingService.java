package com.steveh1uk.stockportfolio.core.pricing;

/**
 *  Service to get the price (in US $ for a stock)
 */
public interface StockPricingService {

    PricingResponse findPriceForStock(PricingRequest pricingRequest);
}
