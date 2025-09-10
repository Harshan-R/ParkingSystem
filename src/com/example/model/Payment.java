package com.example.model;

import java.sql.Timestamp;

public class Payment {
    private int id;
    private int customerId;
    private double amount;
    private String type;
    private String status;
    private Timestamp paymentDate;

    public Payment() {}

    public Payment(int id, int customerId, double amount, String type, String status, Timestamp paymentDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }
}
