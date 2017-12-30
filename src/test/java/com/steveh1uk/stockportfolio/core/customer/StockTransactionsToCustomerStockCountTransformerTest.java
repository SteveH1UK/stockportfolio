package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.ledger.StockTransaction;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StockTransactionsToCustomerStockCountTransformerTest {

    private static final int CUSTOMER_ID = 67436257;

    @Test
    public void transform() {

        StockTransactionsToCustomerStockCountTransformer transformer = new StockTransactionsToCustomerStockCountTransformer(customerStockTransactions());
        assertEquals(expectedResults(), transformer.transform());
    }


    private List<StockTransaction> customerStockTransactions() {

        List<StockTransaction> expectedResultsRequestOne = new ArrayList<>();

        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T02:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("GOOGL").setUnitsBought(20).setUnitsSold(0).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T08:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T11:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T17:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T21:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("BBRY").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T03:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T05:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T06:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(4).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T22:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("MSFT").setUnitsBought(4).setUnitsSold(0).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-03T05:00:00")).setCustomerId(CUSTOMER_ID).setStockCode("NOK").setUnitsBought(4).setUnitsSold(0).build());

        return expectedResultsRequestOne;

    }

    private Map<String, Integer> expectedResults() {
        Map<String, Integer> stockCount = new HashMap<>();
        stockCount.put("MSFT", 4);
        stockCount.put("GOOGL", 16);
        stockCount.put("BBRY", -11);
        stockCount.put("HPQ", -9);
        stockCount.put("NOK", 4);

        return stockCount;
    }

}