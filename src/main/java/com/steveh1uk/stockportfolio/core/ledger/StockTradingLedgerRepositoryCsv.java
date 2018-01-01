package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.ledger.exception.StockLedgerParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *   Could read into a list of stock transactions and sort this by date
 *
 */
@Repository
public class StockTradingLedgerRepositoryCsv implements StockTradingLedgerRepository {

    private final static String STOCK_LEDGER = "stock-trading-ledger.csv";

    private final static String TRADE_DATE_TIME_HEADER = "Date-time of trade";
    private final static String CUSTOMER_ID_HEADER = "Customer ID";
    private final static String STOCK_CODE_HEADER = "Stock Code";
    private final static String UNITS_BOUGHT_HEADER = "Units bought";
    private final static String UNITS_SOLD_HEADER = "Units Sold";

    @Override
    public List<StockTransaction> findCustomerStockTransactions(CustomerStockRequest customerStockRequest) {

        List<StockTransaction> stockTransactions = new ArrayList<>();
        int recordNumber = 1;
        ClassLoader classLoader = this.getClass().getClassLoader();

        try (Reader in = new FileReader(new File(classLoader.getResource(stockLedgerFileName()).getFile())) ) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                StockTransaction currentStockTransaction = new StockTransaction.Builder()
                        .setTradeDateTime(ZonedDateTime.parse(record.get(TRADE_DATE_TIME_HEADER)).toLocalDateTime())
                        .setCustomerId(Integer.parseInt(record.get(CUSTOMER_ID_HEADER)))
                        .setStockCode(record.get(STOCK_CODE_HEADER))
                        .setUnitsBought(parseIntSettingEmptyStringToZero(record.get(UNITS_BOUGHT_HEADER)))
                        .setUnitsSold(parseIntSettingEmptyStringToZero(record.get(UNITS_SOLD_HEADER)))
                        .build();
                if (currentStockTransaction.getTradeDateTime().isAfter(customerStockRequest.getClosingDateCeiling())) {
                    break;
                }
                if (currentStockTransaction.isPartOfCustomerStockRequest(customerStockRequest)) {
                    stockTransactions.add(currentStockTransaction);
                }
                recordNumber ++;
            }
        }
        catch(DateTimeParseException | IllegalArgumentException e) {
            throw new StockLedgerParseException("Error in record " + recordNumber + " within " + stockLedgerFileName() + " because " + e.getMessage(), e);
        }
        catch (IOException | NullPointerException e) {
            throw new StockLedgerParseException("Can not initialise the stock ledger csv file " + stockLedgerFileName(), e);
        }

        return stockTransactions;
    }

    private int parseIntSettingEmptyStringToZero(String value) {
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        else {
            return Integer.parseInt(value);
        }
    }


    // Allow for raining day testing without mocks
    protected String stockLedgerFileName() {
        return STOCK_LEDGER;
    }
}
