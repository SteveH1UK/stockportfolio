package com.steveh1uk.stockportfolio.core.customer;

import com.steveh1uk.stockportfolio.core.ledger.StockTradingLedgerRepository;
import com.steveh1uk.stockportfolio.core.ledger.StockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class CustomerStockServiceImpl implements CustomerStockService {

    @Autowired
    private StockTradingLedgerRepository stockTradingLedgerRepository;

    @Override
    public CustomerStockResult findStockValues(CustomerStockRequest  customerStockRequest) {
        System.out.println("finding stock values");
        validateRequest(customerStockRequest);

        List<StockTransaction> customerStockTransactions = stockTradingLedgerRepository.findCustomerStockTransactions(customerStockRequest);

        StockTransactionsToCustomerStockCountTransformer transformer = new StockTransactionsToCustomerStockCountTransformer(customerStockTransactions);

        return createCustomerStockResult(customerStockRequest, transformer.transform());
    }

    private void validateRequest(CustomerStockRequest customerStockRequest) {

    }
    
    private CustomerStockResult createCustomerStockResult(CustomerStockRequest customerStockRequest, Map<String, Integer> customerStockCount) {

        CustomerStockResult customerStockResult = new CustomerStockResult(customerStockRequest.getCustomerId());

        Set<CustomerStock> customerStocks = new TreeSet<>();
        BigDecimal totalValue = new BigDecimal(0);
        for (Map.Entry<String, Integer> stockCount : customerStockCount.entrySet()
             ) {

            // Get the stock value  from the rest service and then multiple by the number of shares
            BigDecimal stockValue = BigDecimal.ZERO;

            customerStocks.add(new CustomerStock(stockCount.getKey(), stockCount.getValue(), stockValue));

            MathContext mc = new MathContext(2);
            totalValue = totalValue.add(stockValue, mc);

        }
        customerStockResult.setCustomerStocks(customerStocks);
        customerStockResult.setValueSum(totalValue);
        
        return customerStockResult;
    }


}
