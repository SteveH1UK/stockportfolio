package com.steveh1uk.stockportfolio.core.ledger;

import com.steveh1uk.stockportfolio.core.customer.CustomerStockRequest;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  This represents a historic stock transaction and it is recorded as one line in the stock ledger
 *
 *
 */
public class StockTransaction {

    private final LocalDateTime tradeDateTime;
    private final int  customerId;
    private final String  stockCode;
    private final int unitsBought;
    private final int unitsSold;


    private StockTransaction(Builder builder) {
        this.tradeDateTime = builder.tradeDateTime;
        this.customerId = builder.customerId;
        this.stockCode = builder.stockCode;
        this.unitsBought = builder.unitsBought;
        this.unitsSold = builder.unitsSold;
    }

    public static class Builder {
        private LocalDateTime tradeDateTime;
        private int customerId;
        private String stockCode;
        private int unitsBought;
        private int unitsSold;

        public Builder setTradeDateTime(LocalDateTime tradeDateTime) {
            this.tradeDateTime = tradeDateTime;
            return this;
        }

        public Builder setCustomerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setStockCode(String stockCode) {
            this.stockCode = stockCode;
            return this;
        }

        public Builder setUnitsBought(int unitsBought) {
            this.unitsBought = unitsBought;
            return this;
        }

        public Builder setUnitsSold(int unitsSold) {
            this.unitsSold = unitsSold;
            return this;
        }

        public StockTransaction build() {
            return new StockTransaction(this);
        }
    }

    public LocalDateTime getTradeDateTime() {
        return tradeDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public int getUnitsBought() {
        return unitsBought;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    boolean isPartOfCustomerStockRequest(CustomerStockRequest customerStockRequest) {

        return ( (customerId == customerStockRequest.getCustomerId()) &&
                (tradeDateTime.isBefore(customerStockRequest.getClosingDateCeiling())) );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTransaction that = (StockTransaction) o;
        return customerId == that.customerId &&
                unitsBought == that.unitsBought &&
                unitsSold == that.unitsSold &&
                Objects.equals(tradeDateTime, that.tradeDateTime) &&
                Objects.equals(stockCode, that.stockCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tradeDateTime, customerId, stockCode, unitsBought, unitsSold);
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "tradeDateTime=" + tradeDateTime +
                ", customerId=" + customerId +
                ", stockCode='" + stockCode + '\'' +
                ", unitsBought=" + unitsBought +
                ", unitsSold=" + unitsSold +
                '}';
    }
}
