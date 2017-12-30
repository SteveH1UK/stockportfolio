package com.steveh1uk.stockportfolio.core.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class CustomerStockResult {

    private final int customerId;
    private final LocalDateTime requestDate = LocalDateTime.now();
    private BigDecimal valueSum;
    private Set<CustomerStock> customerStocks;

    public CustomerStockResult(int customerId) {
        this.customerId = customerId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public BigDecimal getValueSum() {
        return valueSum;
    }

    public void setValueSum(BigDecimal valueSum) {
        this.valueSum = valueSum;
    }

    public Set<CustomerStock> getCustomerStocks() {
        return customerStocks;
    }

    public void setCustomerStocks(Set<CustomerStock> customerStocks) {
        this.customerStocks = customerStocks;
    }

    @Override
    public String toString() {
        return "CustomerStockResult{" +
                "customerId=" + customerId +
                ", requestDate=" + requestDate +
                ", valueSum=" + valueSum +
                ", customerStocks=" + customerStocks +
                '}';
    }
}
