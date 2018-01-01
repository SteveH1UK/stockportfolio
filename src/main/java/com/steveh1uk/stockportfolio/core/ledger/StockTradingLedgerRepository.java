package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface StockTradingLedgerRepository {


   List<StockTransaction> findCustomerStockTransactions(CustomerStockRequest customerStockRequest);

}
