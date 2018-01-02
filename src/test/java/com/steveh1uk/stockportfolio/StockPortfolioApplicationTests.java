package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStock;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockResult;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import com.steveh1uk.stockportfolio.core.customer.exception.CustomerException;
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

    private static int CUSTOMER_WITH_DATA = 74893279;

	@Autowired
	private CustomerStockService customerStockService;

	@Autowired
	private StockPricingService stockPricingService;

	@Test
	public  void successPath() {

		CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), CUSTOMER_WITH_DATA);

		CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

        System.out.println(customerStockResult);

        // Check symbols and counts. Also check that the order is alphabetic. For values check greater or less than
        assertEquals(CUSTOMER_WITH_DATA, customerStockResult.getCustomerId());
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
                    assertEquals(   "NOK", currentStock.getStockCode());
                    assertEquals(4, currentStock.getUnits());
                    assertTrue(currentStock.getValue().intValue() > 0);
                    break;
            }
        }
	}

    @Test
    public  void noCustomer() {

	    int nonExistingCustomerId = -1;
        CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), nonExistingCustomerId);

        CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

        // Check symbols and counts. Also check that the order is alphabetic. For values check greater or less than
        assertEquals(nonExistingCustomerId, customerStockResult.getCustomerId());
        assertNotNull(customerStockResult.getRequestDate());
        assertTrue("Sum of stock", customerStockResult.getValueSum().intValue() == 0);
        assertEquals("Number of stocks", 0, customerStockResult.getCustomerStocks().size());
    }

	@Test
    public void findEarliestDateViaServiceFacade() {

        LocalDate earliestDate = customerStockService.findEarliestDateOnLedger();

        assertEquals(LocalDate.parse("2017-01-01"), earliestDate);
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

    @Test
    public void mustEnterADate() {

        CustomerStockRequest customerStockRequest = new CustomerStockRequest(null, CUSTOMER_WITH_DATA);

        thrown.expect(CustomerException.class);

        thrown.expectMessage("You must enter a date");

        customerStockService.findStockValues(customerStockRequest);
    }

	@Test
    public void mustNotEnterFutureDate() {

	    LocalDate futureDate = LocalDate.now().plusDays(1L);
	    CustomerStockRequest customerStockRequest = new CustomerStockRequest(futureDate, CUSTOMER_WITH_DATA);

        thrown.expect(CustomerException.class);

        thrown.expectMessage("You must must not enter a date in the future");

        customerStockService.findStockValues(customerStockRequest);
    }


    @Test
    public void mustNotEnterADateBeforeStartOfLedger() {

        LocalDate tooEarlyDate = LocalDate.parse("2016-12-30");
        CustomerStockRequest customerStockRequest = new CustomerStockRequest(tooEarlyDate, CUSTOMER_WITH_DATA);

        thrown.expect(CustomerException.class);

        thrown.expectMessage("You must enter a date after the earliest date on the stock ledger which is 2017-01-01");

        customerStockService.findStockValues(customerStockRequest);
    }
}
