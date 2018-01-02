package com.steveh1uk.stockportfolio.core.pricing;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class StockValueCalculatorTest {

    @Test
    public void multiple() {

        BigDecimal stockUnitPrice = new BigDecimal("9.12");
        int stockUnits = 5;
        assertTrue(StockValueCalculator.multiple(stockUnitPrice, stockUnits).equals(new BigDecimal("45.60")));
    }
}