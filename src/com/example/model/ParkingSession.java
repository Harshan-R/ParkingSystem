package com.example.model;

import java.sql.Timestamp;

public class ParkingSession {
    private int id;
    private int vehicleId;
    private String slotId;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private double charges;
    private int customerId;
    private boolean penaltyApplied;
    private String status; // e.g., "active", "completed", "overdue"

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

    public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean isPenaltyApplied() {
		return penaltyApplied;
	}

	public void setPenaltyApplied(boolean penaltyApplied) {
		this.penaltyApplied = penaltyApplied;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getEntryTime() { return entryTime; }
    public void setEntryTime(Timestamp entryTime) { this.entryTime = entryTime; }

    public Timestamp getExitTime() { return exitTime; }
    public void setExitTime(Timestamp exitTime) { this.exitTime = exitTime; }

    public double getCharges() { return charges; }
    public void setCharges(double charges) { this.charges = charges; }
}
