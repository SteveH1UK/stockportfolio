package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;

import java.time.LocalDate;
import java.util.List;

/**
 *  Repository for the stock trading ledger. It contains all of the stock transactions
 */
public interface StockTradingLedgerRepository {


   List<StockTransaction> findCustomerStockTransactions(CustomerStockRequest customerStockRequest);

   LocalDate findEarliestDateOnLedger();

}
