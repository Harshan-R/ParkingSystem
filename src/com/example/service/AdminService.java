package com.example.service;

import com.example.dao.*;
import com.example.model.*;
import com.example.kafka.KafkaProducerService;

import java.util.List;

public class AdminService {

    private CustomerDAO customerDAO = new CustomerDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    private ParkingSessionDAO sessionDAO = new ParkingSessionDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private TopUpRequestDAO requestDAO = new TopUpRequestDAO();
    private KafkaProducerService kafkaProducer = new KafkaProducerService();

    // View methods
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

    // Top-up approval logic
    public boolean approveTopUp(int requestId) {
        TopUpRequest request = requestDAO.getRequestById(requestId);
        if (request != null && "PENDING".equalsIgnoreCase(request.getStatus())) {
            boolean statusUpdated = requestDAO.updateRequestStatus(requestId, "APPROVED");
            if (statusUpdated) {
                Customer customer = customerDAO.getCustomerById(request.getCustomerId());
                double newBalance = customer.getBalance() + request.getAmount();
                boolean balanceUpdated = customerDAO.updateBalance(customer.getId(), newBalance);

                if (balanceUpdated) {
                    kafkaProducer.sendEvent("topup-approved", "Top-up approved for customerId: " + customer.getId());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean denyTopUp(int requestId) {
        TopUpRequest request = requestDAO.getRequestById(requestId);
        if (request != null && "PENDING".equalsIgnoreCase(request.getStatus())) {
            boolean statusUpdated = requestDAO.updateRequestStatus(requestId, "DENIED");
            if (statusUpdated) {
                kafkaProducer.sendEvent("topup-denied", "Top-up denied for customerId: " + request.getCustomerId());
                return true;
            }
        }
        return false;
    }

    // Slot management
    public boolean addParkingSlot(ParkingSlot slot) {
        return slotDAO.addSlot(slot);
    }

    public boolean changeSlotStatus(String slotId, String newStatus) {
        return slotDAO.updateSlotStatus(slotId, newStatus);
    }

    // Cleanup
    public void shutdown() {
        kafkaProducer.close();
    }
}
