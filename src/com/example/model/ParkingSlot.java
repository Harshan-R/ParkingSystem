package com.example.model;

public class ParkingSlot {
    private String id;
    private String vehicleType;
    private String status;

    public ParkingSlot() {}

    public ParkingSlot(String id, String vehicleType, String status) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
