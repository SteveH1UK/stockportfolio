package com.steveh1uk.stockportfolio.core.customer;

import java.math.BigDecimal;
import java.util.Objects;

public class CustomerStock implements Comparable<CustomerStock> {

    private final String stockCode;
    private final int units;
    private final BigDecimal value;


    public CustomerStock(String stockCode, int units, BigDecimal value) {
        this.stockCode = stockCode;
        this.units = units;
        this.value = value;
    }

    public String getStockCode() {
        return stockCode;
    }

    public int getUnits() {
        return units;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerStock that = (CustomerStock) o;
        return units == that.units &&
                Objects.equals(stockCode, that.stockCode) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stockCode, units, value);
    }

    @Override
    public int compareTo(CustomerStock o) {
        return stockCode.compareTo(o.stockCode);
    }

    @Override
    public String toString() {
        return "CustomerStock{" +
                "stockCode='" + stockCode + '\'' +
                ", units=" + units +
                ", value=" + value.setScale(2).toPlainString() +
                '}';
    }
}
