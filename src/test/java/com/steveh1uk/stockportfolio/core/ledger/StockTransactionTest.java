package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class StockTransactionTest {

    private static final LocalDateTime INCLUDED_DATETIME = LocalDateTime.parse("2017-04-20T06:30:00");
    private static final int SELECTED_CUSTOMER_ID = 3;
    private static final int DIFFERENT_CUSTOMER_ID = 4;

    private static final CustomerStockRequest CUSTOMER_STOCK_REQUEST = new CustomerStockRequest(LocalDate.parse("2017-09-02"), SELECTED_CUSTOMER_ID);
    private static final LocalDateTime LATER_DATETIME = LocalDateTime.parse("2017-09-03T06:30:00");


    @Test
    public void partOfCustomerStockRequest() {

        StockTransaction includedStockTransaction = new StockTransaction.Builder().setTradeDateTime(INCLUDED_DATETIME).setCustomerId(SELECTED_CUSTOMER_ID).setStockCode("APPL").setUnitsBought(0).setUnitsSold(5).build();

        assertTrue(includedStockTransaction.isPartOfCustomerStockRequest(CUSTOMER_STOCK_REQUEST));
    }

    @Test
    public void notPartOfCustomerStockRequestWrongCustomer() {

        StockTransaction includedStockTransaction = new StockTransaction.Builder().setTradeDateTime(INCLUDED_DATETIME).setCustomerId(DIFFERENT_CUSTOMER_ID).setStockCode("APPL").setUnitsBought(0).setUnitsSold(5).build();

        assertFalse(includedStockTransaction.isPartOfCustomerStockRequest(CUSTOMER_STOCK_REQUEST));
    }

    @Test
    public void notPartOfCustomerStockRequesLaterDate() {

        StockTransaction includedStockTransaction = new StockTransaction.Builder().setTradeDateTime(LATER_DATETIME).setCustomerId(SELECTED_CUSTOMER_ID).setStockCode("APPL").setUnitsBought(0).setUnitsSold(5).build();

        assertFalse(includedStockTransaction.isPartOfCustomerStockRequest(CUSTOMER_STOCK_REQUEST));
    }

}