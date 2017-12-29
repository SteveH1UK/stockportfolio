package com.steveh1uk.stockportfolio.core;

import org.springframework.stereotype.Service;

@Service
public class CustomerStockServiceImpl implements CustomerStockService {

    @Override
    public CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest) {
        System.out.println("finding stock values");
        return null;
    }
}
