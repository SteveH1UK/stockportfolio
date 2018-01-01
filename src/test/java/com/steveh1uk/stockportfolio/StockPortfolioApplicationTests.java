package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStock;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockResult;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import com.steveh1uk.stockportfolio.core.pricing.exception.PricingException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 *  This class contains the end to end tests for the service (loading the full Spring application context)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockPortfolioApplicationTests {

	@Autowired
	private CustomerStockService customerStockService;

	@Autowired
	private StockPricingService stockPricingService;

	@Test
	public  void successPath() {

	    int customerId = 74893279;
		CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), customerId);

		CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

        // Check symbols and counts. Also check that the order is alphabetic. For values check greater or less than
        assertEquals(customerId, customerStockResult.getCustomerId());
        assertNotNull(customerStockResult.getRequestDate());
        assertTrue("Sum of stock", customerStockResult.getValueSum().intValue() < 0 );
        assertEquals("Number of stocks", 5, customerStockResult.getCustomerStocks().size());

        int count = 0;
        for (Iterator<CustomerStock> iterator = customerStockResult.getCustomerStocks().iterator(); iterator.hasNext(); count ++) {
            CustomerStock currentStock = iterator.next();
            switch (count) {
                case 0:
                    assertEquals("BB", currentStock.getStockCode());
                    assertEquals(-11, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() < 0);
                    break;
                case 1:
                    assertEquals("GOOGL", currentStock.getStockCode());
                    assertEquals(-5, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() < 0);
                    break;
                case 2:
                    assertEquals("HPQ", currentStock.getStockCode());
                    assertEquals(-9, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() < 0);
                    break;
                case 3:
                    assertEquals("MSFT", currentStock.getStockCode());
                    assertEquals(4, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() > 0);
                    break;
                case 4:
                    assertEquals("NOK", currentStock.getStockCode());
                    assertEquals(4, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() > 0);
                    break;
            }
        }
	}


	@Test
	public void priceQuote() {

		String googleStockCode = "GOOGL";
		PricingRequest pricingRequest = new PricingRequest(googleStockCode);

		PricingResponse pricingResponse = stockPricingService.findPriceForStock(pricingRequest);

		BigDecimal googleStockPrice = pricingResponse.getStockUnitPrice();

		System.out.println("Google's closing price is " + googleStockPrice);

		String minimumGoogleStockPrice = "500";
		assertTrue("Price varies but unlikely to fall beneath $" + minimumGoogleStockPrice, ( googleStockPrice. compareTo(new BigDecimal(minimumGoogleStockPrice)) > 0));
	}

    @Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void priceQuoteStockNotFound() {

		String notFoundStockCode = "NOTFOUND";
		PricingRequest pricingRequest = new PricingRequest(notFoundStockCode);

        thrown.expect(PricingException.class);

        thrown.expectMessage("HTTP Error calling https://api.iextrading.com/1.0/stock/NOTFOUND/open-close with status Code 404");

        thrown.expectCause(IsInstanceOf.instanceOf(HttpClientErrorException.class));

		stockPricingService.findPriceForStock(pricingRequest);

	}

}
