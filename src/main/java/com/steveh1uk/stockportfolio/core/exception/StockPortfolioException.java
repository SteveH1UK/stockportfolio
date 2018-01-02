package com.steveh1uk.stockportfolio.core.exception;

/**
 *  Thrown when we get an exception within the core Stock Polio module
 */
public class StockPortfolioException extends RuntimeException {

    protected StockPortfolioException(String message) {
        super(message);
    }


    protected StockPortfolioException(String message, Throwable cause) {
        super(message, cause);
    }
}
