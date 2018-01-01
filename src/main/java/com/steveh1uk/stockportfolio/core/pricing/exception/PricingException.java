package com.steveh1uk.stockportfolio.core.pricing.exception;

import com.steveh1uk.stockportfolio.core.exception.StockPortfolioException;

public class PricingException  extends StockPortfolioException {

    public PricingException(String message, Throwable cause) {
        super(message, cause);
    }
}
