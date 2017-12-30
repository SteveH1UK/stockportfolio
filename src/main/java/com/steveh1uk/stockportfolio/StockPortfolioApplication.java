package com.steveh1uk.stockportfolio;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import static java.lang.System.exit;

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
	public void run(String... args) throws Exception {

		if (args.length > 2) {
			customerStockService.findStockValues(null);
		}
		else {
			System.out.println("No parameters entered - ignoring request");
		}
	}
}
