package com.steveh1uk.stockportfolio.core;

public interface CustomerStockService {

    CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest);
}
