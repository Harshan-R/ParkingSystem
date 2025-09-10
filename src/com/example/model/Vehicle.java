package com.example.model;

public class Vehicle {
    private int id;
    private int customerId;
    private String vehicleType;
    private String numberPlate;

    public Vehicle() {}

    public Vehicle(int id, int customerId, String vehicleType, String numberPlate) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleType = vehicleType;
        this.numberPlate = numberPlate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getNumberPlate() { return numberPlate; }
    public void setNumberPlate(String numberPlate) { this.numberPlate = numberPlate; }
}
