package com.example.service;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

public class LoginService {

    private CustomerDAO customerDAO = new CustomerDAO();
    private CustomerService customerService = new CustomerService();

    // Admin login check
    public boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }

    // Authenticate existing customer by email or phone
    public Customer authenticateCustomer(String emailOrPhone) {
        return customerDAO.findCustomerByEmailOrPhone(emailOrPhone);
    }

    // Register new customer
    public boolean registerCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    // Check for overdue sessions after login
    public boolean hasOverdueSessions(int customerId) {
        return customerService.hasOverdueSessions(customerId);
    }

    // Optional: Check if customer has sufficient balance before parking
    public boolean hasSufficientBalance(int customerId, double requiredAmount) {
        return customerService.hasSufficientBalance(customerId, requiredAmount);
    }
}
