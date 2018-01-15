package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.ledger.StockTradingLedgerRepository;
import com.steveh1uk.stockportfolio.core.ledger.StockTransaction;
import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import mockit.Expectations;
import mockit.Injectable;

import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 *   Expand the unit testing using mock objects. Here we are not dependent on the external pricing engine to test
 *   the pricing and so the integration testing is more than in the StockValueCalculatorTest
 */
@RunWith(JMockit.class)
public class CustomerStockServiceImplTest {

    @Tested
    private CustomerStockServiceImpl customerStockService;

    @Injectable
    private StockPricingService stockPricingService;

    @Injectable
    private StockTradingLedgerRepository stockTradingLedgerRepository;

    @Test
    public void findStockValuesForOneStock() {

        int customerId = 700;
        int stockValue = 500;

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.now(), customerId);

        new Expectations() {{

            stockPricingService.findPriceForStock((PricingRequest) any) ; result = new PricingResponse(new BigDecimal(stockValue)); times = 1;

            stockTradingLedgerRepository.findCustomerStockTransactions(customerStockRequest); result = oneStockTransaction();
        }

            private List<StockTransaction> oneStockTransaction() {
                List<StockTransaction> stockTransactions = new ArrayList<>();
                stockTransactions.add( new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T02:00:00")).setCustomerId(customerId).setStockCode("GOOGL").setUnitsBought(14).setUnitsSold(0).build());
                stockTransactions.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T08:00:00")).setCustomerId(customerId).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(5).build());

                return stockTransactions;
            }
        };


        CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

        assertEquals(customerId, customerStockResult.getCustomerId());
        assertNotNull(customerStockResult.getRequestDate());
        assertEquals("Sum of stock", 9 * stockValue, customerStockResult.getValueSum().intValue() );
        assertEquals("Number of types of stocks", 1, customerStockResult.getCustomerStocks().size());

        Iterator<CustomerStock>  iterator = customerStockResult.getCustomerStocks().iterator();
        CustomerStock currentStock = iterator.next();
        assertEquals("GOOGL", currentStock.getStockCode());
        assertEquals(9, currentStock.getUnits());
        assertEquals(9 * stockValue, currentStock.getValue().intValue() );

    }

}