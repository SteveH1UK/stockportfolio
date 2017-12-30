package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.ledger.StockTransaction;

import java.util.*;

/*
   This class is responsible for transforming a list of Stock Transactions to a map with a count of Customer Stock (for
   a point in time).

   What is missing is the starting balance for the customer (outside scope)
 */
public class StockTransactionsToCustomerStockCountTransformer {

    private final List<StockTransaction> stockTransactions;

    public StockTransactionsToCustomerStockCountTransformer(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }


    public Map<String, Integer> transform() {

        Map<String, Integer> stockCount = new HashMap<>();

        for (StockTransaction stockTransaction : stockTransactions
             ) {
            if (stockCount.containsKey(stockTransaction.getStockCode())) {
                int currentStockCount = stockCount.get(stockTransaction.getStockCode());
                stockCount.put(stockTransaction.getStockCode(), currentStockCount + stockTransaction.getUnitsBought() - stockTransaction.getUnitsSold());
            }
            else {
                stockCount.put(stockTransaction.getStockCode(), stockTransaction.getUnitsBought() - stockTransaction.getUnitsSold());
            }
        }
        return stockCount;
    }
}
