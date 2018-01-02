package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.exception.StockPortfolioException;

import java.time.LocalDate;

/**
 *  This class acts as the service facade for requests for customer stock value on a specified date
 */
public interface CustomerStockService {

    /**
     * @return - earliest date on the stock ledger (that a customer can query their stock values with)
     */
    LocalDate findEarliestDateOnLedger();

    /**
     * Returns the requested Customer stock data
     * @param customerStockRequest - specified date and the customer id
     * @return - a result record with the list of customer stock
     * @throws StockPortfolioException - this will contain the error message. When this error was caused by a standard or API exception then the initial exception will be wrapped within this custom exception.
     */
    CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest) throws StockPortfolioException;
}
