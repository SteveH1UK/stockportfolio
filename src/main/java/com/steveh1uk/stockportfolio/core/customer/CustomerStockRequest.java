package com.steveh1uk.stockportfolio.core.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *  A request for a customer's stocks for a specific date
 */
public class CustomerStockRequest {

    private final LocalDate selectedDate;
    // Start of the following day for closing date to help date comparisons
    private final LocalDateTime closingDateCeiling;
    private final int customerId;

    public CustomerStockRequest(LocalDate selectedDate, int customerId) {
        this.selectedDate = selectedDate;
        this.closingDateCeiling = selectedDate.plusDays(1L).atStartOfDay();
        this.customerId = customerId;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public LocalDateTime getClosingDateCeiling() {
        return closingDateCeiling;
    }

    public int getCustomerId() {
        return customerId;
    }
}
