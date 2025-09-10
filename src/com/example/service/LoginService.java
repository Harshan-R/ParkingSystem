package com.example.service;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

public class LoginService {

    private CustomerDAO customerDAO = new CustomerDAO();

    public boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }

    public Customer authenticateCustomer(String emailOrPhone) {
        return customerDAO.findCustomerByEmailOrPhone(emailOrPhone);
    }
}
