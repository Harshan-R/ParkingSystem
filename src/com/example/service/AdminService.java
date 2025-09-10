package com.example.service;

import com.example.dao.*;
import com.example.model.*;

import java.util.List;

public class AdminService {

    private CustomerDAO customerDAO = new CustomerDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    private ParkingSessionDAO sessionDAO = new ParkingSessionDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private TopUpRequestDAO requestDAO = new TopUpRequestDAO();

    public List<Customer> viewAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public List<Vehicle> viewAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    public List<ParkingSlot> viewAllSlots() {
        return slotDAO.getAllSlots();
    }

    public List<ParkingSession> viewAllSessions() {
        return sessionDAO.getAllSessions();
    }

    public List<Payment> viewAllPayments() {
        return paymentDAO.getAllPayments();
    }

    public List<TopUpRequest> viewAllTopUpRequests() {
        return requestDAO.getAllRequests();
    }

    public boolean approveTopUp(int requestId) {
        return requestDAO.updateRequestStatus(requestId, "APPROVED");
    }

    public boolean denyTopUp(int requestId) {
        return requestDAO.updateRequestStatus(requestId, "DENIED");
    }

    public boolean addParkingSlot(ParkingSlot slot) {
        return slotDAO.addSlot(slot);
    }

    public boolean changeSlotStatus(String slotId, String newStatus) {
        return slotDAO.updateSlotStatus(slotId, newStatus);
    }
}
