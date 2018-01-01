package com.steveh1uk.stockportfolio.core.exception;

/**
 *  Thrown when we get an exception within the core Stock Polio module
 */
public class StockPortfolioException extends RuntimeException {

    public StockPortfolioException(String message, Throwable cause) {
        super(message, cause);
    }
}
