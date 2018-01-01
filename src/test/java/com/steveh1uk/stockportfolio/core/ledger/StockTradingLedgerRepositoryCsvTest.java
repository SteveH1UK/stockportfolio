package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.ledger.exception.StockLedgerParseException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StockTradingLedgerRepositoryCsvTest {


    private static final int CUSTOMER_ID_REQUEST_ONE = 74893279;

    @Test
    public void findCustomerStockTransactionsWhenPresent() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), CUSTOMER_ID_REQUEST_ONE);

        List<StockTransaction> stockTransactions = new StockTradingLedgerRepositoryCsv().findCustomerStockTransactions(customerStockRequest);

        assertEquals("Result size differs", 10, stockTransactions.size());
        assertEquals("Lists are NOT the same", expectedResultsForRequestOne(), stockTransactions);
    }

    private List<StockTransaction> expectedResultsForRequestOne() {

        List<StockTransaction> expectedResultsRequestOne = new ArrayList<>();

        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T02:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(1).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T08:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T11:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BB").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T17:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BB").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-01T21:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("BB").setUnitsBought(0).setUnitsSold(3).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T03:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(5).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T05:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("GOOGL").setUnitsBought(0).setUnitsSold(2).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T06:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("HPQ").setUnitsBought(0).setUnitsSold(4).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-02T22:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("MSFT").setUnitsBought(4).setUnitsSold(0).build());
        expectedResultsRequestOne.add(new StockTransaction.Builder().setTradeDateTime(LocalDateTime.parse("2017-01-03T05:00:00")).setCustomerId(CUSTOMER_ID_REQUEST_ONE).setStockCode("NOK").setUnitsBought(4).setUnitsSold(0).build());

        return expectedResultsRequestOne;

    }


    /*
     * ==================================================================================================
     *                                     Rainy day tests
     * ==================================================================================================
     */


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void stockLedgerNotFound() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), CUSTOMER_ID_REQUEST_ONE);
        thrown.expect(StockLedgerParseException.class);

        thrown.expectMessage("Can not initialise the stock ledger csv file missing-stock-ledger.csv");

        thrown.expectCause(IsInstanceOf.instanceOf(FileNotFoundException.class));

        new StockTradingLedgerRepositoryCsv() {
            protected String stockLedgerFileName() {
                return "missing-stock-ledger.csv";
            }
        }.findCustomerStockTransactions(customerStockRequest);
    }


    @Test
    public void invalidDateTimeOfTradeInStockLedger() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 4);

        thrown.expect(StockLedgerParseException.class);

        thrown.expectMessage("Error in record 5 within invalid-trade-date-stock-trading-ledger.csv because Text '2017-Jan-01T04:00:00Z' could not be parsed at index 5");

        thrown.expectCause(IsInstanceOf.instanceOf(DateTimeParseException.class));

        new StockTradingLedgerRepositoryCsv() {
            protected String stockLedgerFileName() {
                return "invalid-trade-date-stock-trading-ledger.csv";
            }
        }.findCustomerStockTransactions(customerStockRequest);
    }



    @Test
    public void invalidCustomerIdInStockLedger() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 45); // Any Customer is OK

        thrown.expect(StockLedgerParseException.class);

        thrown.expectMessage("Error in record 3 within invalid-customer-id-stock-trading-ledger.csv because For input string: \"CUST-4\"");

        thrown.expectCause(IsInstanceOf.instanceOf(NumberFormatException.class));

        new StockTradingLedgerRepositoryCsv() {
            protected String stockLedgerFileName() {
                return "invalid-customer-id-stock-trading-ledger.csv";
            }
        }.findCustomerStockTransactions(customerStockRequest);
    }

    @Test
    public void invalidStockBoughtInStockLedger() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 45); // Any Customer is OK

        thrown.expect(StockLedgerParseException.class);

        thrown.expectMessage("Error in record 2 within invalid-stock-bought-stock-trading-ledger.csv because For input string: \"FIVE\"");

        thrown.expectCause(IsInstanceOf.instanceOf(NumberFormatException.class));

        new StockTradingLedgerRepositoryCsv() {
            protected String stockLedgerFileName() {
                return "invalid-stock-bought-stock-trading-ledger.csv";
            }
        }.findCustomerStockTransactions(customerStockRequest);
    }

    @Test
    public void invalidStockSoldInStockLedger() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 45); // Any Customer is OK

        thrown.expect(StockLedgerParseException.class);

        thrown.expectMessage("Error in record 22 within invalid-stock-sold-stock-trading-ledger.csv because For input string: \"3H\"");

        thrown.expectCause(IsInstanceOf.instanceOf(NumberFormatException.class));

        new StockTradingLedgerRepositoryCsv() {
            protected String stockLedgerFileName() {
                return "invalid-stock-sold-stock-trading-ledger.csv";
            }
        }.findCustomerStockTransactions(customerStockRequest);
    }

}