package com.example.model;

import java.sql.Timestamp;

public class TopUpRequest {
    private int id;
    private int customerId;
    private double amount;
    private String status;
    private Timestamp requestDate;

    public TopUpRequest() {}

    public TopUpRequest(int id, int customerId, double amount, String status, Timestamp requestDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getRequestDate() { return requestDate; }
    public void setRequestDate(Timestamp requestDate) { this.requestDate = requestDate; }
}
