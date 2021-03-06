package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.ledger.exception.StockLedgerParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockTradingLedgerRepositoryCsv implements StockTradingLedgerRepository {

    private final static String STOCK_LEDGER = "stock-trading-ledger.csv";

    private final static String TRADE_DATE_TIME_HEADER = "Date-time of trade";
    private final static String CUSTOMER_ID_HEADER = "Customer ID";
    private final static String STOCK_CODE_HEADER = "Stock Code";
    private final static String UNITS_BOUGHT_HEADER = "Units bought";
    private final static String UNITS_SOLD_HEADER = "Units Sold";

    private static final String CAN_NOT_INITIALISE_THE_STOCK_LEDGER_CSV_FILE = "Can not initialise the stock ledger csv file ";

    @Autowired
    ResourceLoader resourceloader;

    @Override
    public List<StockTransaction> findCustomerStockTransactions(CustomerStockRequest customerStockRequest) {

        List<StockTransaction> stockTransactions = new ArrayList<>();
        int recordNumber = 1;
        try (Reader in = csvReader()) {

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
            throw new StockLedgerParseException(CAN_NOT_INITIALISE_THE_STOCK_LEDGER_CSV_FILE + stockLedgerFileName(), e);
        }

        return stockTransactions;
    }

    @Override
    public LocalDate findEarliestDateOnLedger() {

        try (Reader in = csvReader()) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            CSVRecord record = records.iterator().next();
            LocalDateTime earliestDate = ZonedDateTime.parse(record.get(TRADE_DATE_TIME_HEADER)).toLocalDateTime();
            return earliestDate.toLocalDate();

        }
        catch(DateTimeParseException | IllegalArgumentException e) {
            throw new StockLedgerParseException("Error in first data record within " + stockLedgerFileName() + " because " + e.getMessage(), e);
        }
        catch (IOException | NullPointerException e) {
            throw new StockLedgerParseException(CAN_NOT_INITIALISE_THE_STOCK_LEDGER_CSV_FILE + stockLedgerFileName(), e);
        }
    }

    private Reader csvReader() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource(stockLedgerFileName());
        return new InputStreamReader(classPathResource.getInputStream());
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
