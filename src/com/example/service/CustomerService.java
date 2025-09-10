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
}
