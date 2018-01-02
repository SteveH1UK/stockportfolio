package com.steveh1uk.stockportfolio.core.pricing.iextrading;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/*
Java Bean for the response for a open-close request on the Iextrading API

Example json

{
  "open": {
    "price": 154,
    "time": 1506605400394
  },
  "close": {
    "price": 153.28,
    "time": 1506605400394
  },
}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class OpenCloseResponseIextrading {

    private PriceTimeIextrading open;

    private PriceTimeIextrading close;

    public PriceTimeIextrading getOpen() {
        return open;
    }

    public void setOpen(PriceTimeIextrading open) {
        this.open = open;
    }

    public PriceTimeIextrading getClose() {
        return close;
    }

    public void setClose(PriceTimeIextrading close) {
        this.close = close;
    }

}
