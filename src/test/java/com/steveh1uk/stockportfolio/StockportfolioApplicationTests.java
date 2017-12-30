package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockResult;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

/**
 *  This class contains the end to end tests for the service (loading the full Spring application context)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockportfolioApplicationTests {

	@Autowired
	private CustomerStockService customerStockService;

	@Test
	public  void successPath() {

		CustomerStockRequest customerStockRequest = new CustomerStockRequest(LocalDate.parse("2017-01-03"), 74893279);

		CustomerStockResult customerStockResult = customerStockService.findStockValues(customerStockRequest);

		System.out.println("Customer Stocks " + customerStockResult);
	}

}
