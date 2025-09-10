package com.example.service;

import com.example.dao.*;
import com.example.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    private ParkingSessionDAO sessionDAO = new ParkingSessionDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private TopUpRequestDAO requestDAO = new TopUpRequestDAO();

    public Customer getCustomerById(int id) {
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean registerVehicle(Vehicle vehicle) {
        return vehicleDAO.registerVehicle(vehicle);
    }

    public boolean requestTopUp(TopUpRequest request) {
        return requestDAO.createRequest(request);
    }

    public List<Payment> getPaymentsByCustomer(int customerId) {
        List<Payment> all = paymentDAO.getAllPayments();
        return all.stream().filter(p -> p.getCustomerId() == customerId).collect(Collectors.toList());
    }

    public List<ParkingSlot> getAvailableSlots(String vehicleType) {
        List<ParkingSlot> all = slotDAO.getAllSlots();
        return all.stream()
                .filter(s -> s.getVehicleType().equals(vehicleType) && s.getStatus().equals("FREE"))
                .collect(Collectors.toList());
    }

    public boolean startParkingSession(ParkingSession session) {
        boolean started = sessionDAO.startSession(session);
        if (started) {
            slotDAO.updateSlotStatus(session.getSlotId(), "OCCUPIED");
        }
        return started;
    }

    public boolean endParkingSession(int sessionId, String slotId, double charges) {
        Timestamp exitTime = new Timestamp(System.currentTimeMillis());
        boolean ended = sessionDAO.endSession(sessionId, exitTime, charges);
        if (ended) {
            slotDAO.updateSlotStatus(slotId, "FREE");
            return paymentDAO.addPayment(new Payment(0, getCustomerIdBySession(sessionId), charges, "PARKING_FEE", "APPROVED", exitTime));
        }
        return false;
    }

    public boolean registerNewCustomer(Customer customer) {
    return customerDAO.addCustomer(customer);
}
    public List<Vehicle> getVehiclesByCustomer(int customerId) {
        return vehicleDAO.getAllVehicles().stream()
                .filter(v -> v.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public Vehicle getVehicleByPlate(String numberPlate) {
        return vehicleDAO.getAllVehicles().stream()
                .filter(v -> v.getNumberPlate().equalsIgnoreCase(numberPlate))
                .findFirst().orElse(null);
    }

    public ParkingSession getActiveSessionByVehicle(int vehicleId) {
        return sessionDAO.getAllSessions().stream()
                .filter(s -> s.getVehicleId() == vehicleId && s.getExitTime() == null)
                .findFirst().orElse(null);
    }

    public double calculateCharges(Timestamp entryTime, String vehicleType) {
        long durationHours = java.time.Duration.between(entryTime.toInstant(), 
                                new Timestamp(System.currentTimeMillis()).toInstant()).toHours();

        double rate;
        switch (vehicleType.toUpperCase()) {
            case "TWO_WHEELER": rate = 10.0; break;
            case "FOUR_WHEELER": rate = 30.0; break;
            case "SIX_WHEELER": rate = 50.0; break;
            default: rate = 20.0; // fallback rate
        }

        double baseCharge = durationHours * rate;

        // Apply 5% penalty if overdue (more than 1 hour)
        if (durationHours > 1) {
            double penalty = baseCharge * 0.05;
            return baseCharge + penalty;
        }

        return baseCharge;
    }



    public boolean hasOverdueSessions(int customerId) {
        List<ParkingSession> sessions = sessionDAO.getAllSessions();
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();

        long now = System.currentTimeMillis();

        for (ParkingSession session : sessions) {
            if (session.getExitTime() == null) {
                Vehicle v = vehicles.stream()
                    .filter(veh -> veh.getId() == session.getVehicleId() && veh.getCustomerId() == customerId)
                    .findFirst().orElse(null);

                if (v != null) {
                    long entry = session.getEntryTime().getTime();
                    if ((now - entry) > 3600000) { // 1 hour in milliseconds
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getCustomerIdBySession(int sessionId) {
        List<ParkingSession> sessions = sessionDAO.getAllSessions();
        for (ParkingSession s : sessions) {
            if (s.getId() == sessionId) {
                Vehicle v = vehicleDAO.getAllVehicles().stream()
                        .filter(veh -> veh.getId() == s.getVehicleId())
                        .findFirst().orElse(null);
                return v != null ? v.getCustomerId() : -1;
            }
        }
        return -1;
    }

    // ✅ New method: Balance check before parking
    public boolean hasSufficientBalance(int customerId, double requiredAmount) {
        Customer customer = customerDAO.getCustomerById(customerId);
        return customer != null && customer.getBalance() >= requiredAmount;
    }
}
