package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockResult;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

/**
 *  This class contains the end to end tests for the service (loading the full Spring application context)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockportfolioApplicationTests {

	@Autowired
	private CustomerStockService customerStockService;

	@Autowired
	private StockPricingService stockPricingService;

	@Test
	public  void successPath() {

		CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 74893279);

		CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

		System.out.println("Customer Stocks " + customerStockResult);
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

}
