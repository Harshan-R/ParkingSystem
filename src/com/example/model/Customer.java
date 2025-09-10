package com.example.model;

import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private double balance;
    private List<ParkingSession> sessions;
    private List<Vehicle> vehicles;

    public Customer() {}

    public Customer(int id, String name, String phone, String email, double balance) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.balance = balance;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ParkingSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<ParkingSession> sessions) {
		this.sessions = sessions;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
