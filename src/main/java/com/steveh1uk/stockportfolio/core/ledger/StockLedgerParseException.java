package com.steveh1uk.stockportfolio.core.ledger;

/**
 *  Thrown when we get an exception processing the Stock Ledger
 */
public class StockLedgerParseException extends RuntimeException {

    public StockLedgerParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
