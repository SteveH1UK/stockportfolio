package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.customer.exception.CustomerException;
import com.steveh1uk.stockportfolio.core.exception.StockPortfolioException;
import com.steveh1uk.stockportfolio.core.ledger.StockTradingLedgerRepository;
import com.steveh1uk.stockportfolio.core.ledger.StockTransaction;
import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import com.steveh1uk.stockportfolio.core.pricing.StockValueCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class CustomerStockServiceImpl implements CustomerStockService {

    @Autowired
    private StockPricingService stockPricingService;

    @Autowired
    private StockTradingLedgerRepository stockTradingLedgerRepository;


    @Override
    public LocalDate findEarliestDateOnLedger() {
        return stockTradingLedgerRepository.findEarliestDateOnLedger();
    }

    @Override
    public CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest) throws StockPortfolioException {

        validateRequest(customerStockRequest);

        List<StockTransaction> customerStockTransactions = stockTradingLedgerRepository.findCustomerStockTransactions(customerStockRequest);

        StockTransactionsToCustomerStockCountTransformer transformer = new StockTransactionsToCustomerStockCountTransformer(customerStockTransactions);

        return createCustomerStockResult(customerStockRequest, transformer.transform());
    }

    /*
    These are sanity tests since it is assumed that client inputs correct data - hence runtime error
     */
    private void validateRequest(CustomerStockRequest customerStockRequest) {

        if (customerStockRequest.getSelectedDate() == null) {
            throw new CustomerException("You must enter a date");
        }

        LocalDate earliestDate = stockTradingLedgerRepository.findEarliestDateOnLedger();
        if (customerStockRequest.getSelectedDate().isBefore(earliestDate)) {
            throw new CustomerException("You must enter a date after the earliest date on the stock ledger which is " + earliestDate);
        }

        if (customerStockRequest.getSelectedDate().isAfter(LocalDate.now())) {
            throw new CustomerException("You must must not enter a date in the future");
        }
    }
    
    private CustomerStockResult createCustomerStockResult(CustomerStockRequest customerStockRequest, Map<String, Integer> customerStockCount) {

        CustomerStockResult customerStockResult = new CustomerStockResult(customerStockRequest.getCustomerId());

        Set<CustomerStock> customerStocks = new TreeSet<>();
        BigDecimal totalValue = new BigDecimal(0);
        for (Map.Entry<String, Integer> stockCount : customerStockCount.entrySet()
             ) {

            BigDecimal stockValue = findStockPrice(stockCount);

            customerStocks.add(new CustomerStock(stockCount.getKey(), stockCount.getValue(), stockValue));

            totalValue = totalValue.add(stockValue);
        }
        customerStockResult.setCustomerStocks(customerStocks);
        customerStockResult.setValueSum(totalValue);
        
        return customerStockResult;
    }

    private BigDecimal findStockPrice(Map.Entry<String, Integer> stockCount) {

        PricingRequest pricingRequest = new PricingRequest(stockCount.getKey());

        PricingResponse pricingResponse = stockPricingService.findPriceForStock(pricingRequest);

        return StockValueCalculator.multiple(pricingResponse.getStockUnitPrice(), stockCount.getValue());
    }


}
