package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.exception.StockPortfolioException;

/**
 *  This class acts as the service facade for requests for customer stock value on a specified date
 */
public interface CustomerStockService {

    /**
     * Returns the requested Customer stock data
     * @param customerStockRequest - specified date and the customer id
     * @return - a result record with the list of customer stock
     * @throws StockPortfolioException - this will wrap the unexpected exception caught within the application together with a useful message
     */
    CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest) throws StockPortfolioException;
}
