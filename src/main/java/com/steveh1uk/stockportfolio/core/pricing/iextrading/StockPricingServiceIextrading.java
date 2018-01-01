package com.steveh1uk.stockportfolio.core.pricing.iextrading;

import com.steveh1uk.stockportfolio.core.pricing.PricingRequest;
import com.steveh1uk.stockportfolio.core.pricing.PricingResponse;
import com.steveh1uk.stockportfolio.core.pricing.StockPricingService;
import com.steveh1uk.stockportfolio.core.pricing.exception.PricingException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StockPricingServiceIextrading implements StockPricingService {

    @Override
    public PricingResponse findPriceForStock(PricingRequest pricingRequest) {

        String url = IextradingConstants.IEXTRADING_STOCK_API_URL + pricingRequest.getStockCode() + "/open-close";

        try {

            RestTemplate restTemplate = new RestTemplate();

            OpenCloseResponseIextrading openCloseResponseIextrading = restTemplate.getForObject(url, OpenCloseResponseIextrading.class);

            return new PricingResponse(new BigDecimal(openCloseResponseIextrading.getClose().getPrice()));
        }
        catch (HttpStatusCodeException e){
            throw new PricingException("HTTP Error calling " + url + " with status Code " + e.getStatusCode().value(), e);
        }

    }
}
