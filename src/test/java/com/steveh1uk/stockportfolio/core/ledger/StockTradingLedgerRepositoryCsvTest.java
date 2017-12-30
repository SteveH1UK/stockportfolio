package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StockTradingLedgerRepositoryCsvTest {


    private static final int CUSTOMER_ID_REQUEST_ONE = 74893279;

    @Test
    public void findCustomerStockTransactionsWhenPresent() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), CUSTOMER_ID_REQUEST_ONE);

        List<StockTransaction> stockTransactions = new StockTradingLedgerRepositoryCsv().findCustomerStockTransactions(customerStockRequest);

        assertEquals("Result size differs", 10, stockTransactions.size());

        System.out.println(stockTransactions.size() + "" + stockTransactions);

        assertEquals("Lists are NOT the same", expectedResultsForRequestOne(), stockTransactions);

    }
    
    private List<StockTransaction> expectedResultsForRequestOne() {

        List<StockTransaction> expectedResultsRequestOne = new ArrayList<>();

        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T02:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(1).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T08:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T11:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T17:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T21:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T03:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T05:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T06:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(4).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T22:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("MSFT").setUnitsBought(4).setUnitsSold(0).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-03T05:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("NOK").setUnitsBought(4).setUnitsSold(0).build());

        return expectedResultsRequestOne;
        
    }

}