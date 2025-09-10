package com.example.model;

import java.sql.Timestamp;

public class ParkingSession {
    private int id;
    private int vehicleId;
    private String slotId;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private double charges;

    public ParkingSession() {}

    public ParkingSession(int id, int vehicleId, String slotId, Timestamp entryTime, Timestamp exitTime, double charges) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.charges = charges;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }

    public Timestamp getEntryTime() { return entryTime; }
    public void setEntryTime(Timestamp entryTime) { this.entryTime = entryTime; }

    public Timestamp getExitTime() { return exitTime; }
    public void setExitTime(Timestamp exitTime) { this.exitTime = exitTime; }

    public double getCharges() { return charges; }
    public void setCharges(double charges) { this.charges = charges; }
}
