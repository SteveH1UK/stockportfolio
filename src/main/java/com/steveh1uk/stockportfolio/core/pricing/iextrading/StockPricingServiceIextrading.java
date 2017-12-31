package com.steveh1uk.stockportfolio.core.pricing.iextrading;

import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StockPricingServiceIextrading implements StockPricingService {

    // TODO - Exception handling (service down, stock not found)
    @Override
    public PricingResponse findPriceForStock(PricingRequest pricingRequest) {

        RestTemplate restTemplate = new RestTemplate();

        String url = IextradingConstants.IEXTRADING_STOCK_API_URL + pricingRequest.getStockCode() + "/open-close";

        OpenCloseResponseIextrading openCloseResponseIextrading = restTemplate.getForObject(url, OpenCloseResponseIextrading.class);

        return new PricingResponse(new BigDecimal(openCloseResponseIextrading.getClose().getPrice()));

    }
}
