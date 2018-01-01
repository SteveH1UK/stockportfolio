package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockResult;
import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;

/**
 *  Run a stock request from the command line
 *  Use parameters:
 *  1 = Closing date (e.g. 2017-01-03)
 *  2 = Stock Code (e.g. GOOGL for Google inc)
 */
@SpringBootApplication
public class StockPortfolioApplication implements CommandLineRunner {

	@Autowired
	private CustomerStockService customerStockService;

	/**
	 * Run stock portfolio on the command line
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(StockPortfolioApplication.class);

		application.run(args);

	}

	@Override
	public void run(String... args) {

		if (args.length == 2) {
			CustomerStockResult  customerStockResult  = customerStockService.findStockValues(createCustomerStockRequest(args));
            System.out.println(customerStockResult);
		}
		else {
			System.out.println("Incorrect args - ignoring request");
            System.out.println("Parameter 1 = selected date in YYYY-MM-DD format (e.g. 2017-01-02)");
            System.out.println("Parameter 2 = customer id (e.g. 74893279");
		}
	}

	private CustomerStockRequest createCustomerStockRequest(String... args) {

        LocalDate selectedDate = LocalDate.parse(args[0]);
        int customerId = Integer.parseInt(args[1]);

		return new CustomerStockRequest(selectedDate, customerId);
	}
}
