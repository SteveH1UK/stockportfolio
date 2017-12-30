package com.steveh1uk.stockportfolio.core.customer;

public interface CustomerStockService {

    CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest);
}
