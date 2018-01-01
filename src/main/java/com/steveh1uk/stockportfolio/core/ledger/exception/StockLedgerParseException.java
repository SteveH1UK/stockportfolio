package com.steveh1uk.stockportfolio.core.ledger.exception;

import com.steveh1uk.stockportfolio.core.exception.StockPortfolioException;

/**
 *  Thrown when we get an exception processing the Stock Ledger
 */
public class StockLedgerParseException extends StockPortfolioException {

    public StockLedgerParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
