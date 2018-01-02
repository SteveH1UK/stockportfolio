package com.steveh1uk.stockportfolio.core.pricing;

import java.math.BigDecimal;

/**
 *  Calculates the value of the stock from it's price and number
 */
public final class StockValueCalculator {

    public static BigDecimal multiple(BigDecimal stockUnitPrice, int stockUnits) {
        return stockUnitPrice.multiply(new BigDecimal(stockUnits));
    }
}
