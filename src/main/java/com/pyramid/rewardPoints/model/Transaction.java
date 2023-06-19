package com.pyramid.rewardPoints.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private String customerId;
    private double amount;
    private LocalDate date;

    public Transaction(String customerId, double amount, LocalDate date) {
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
    }
}
