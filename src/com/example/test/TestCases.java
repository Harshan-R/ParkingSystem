//package com.example.test;
//
//import com.example.dao.CustomerDAO;
//import com.example.model.Customer;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.util.List;
//
//public class TestCases {
//
//    @Test
//    public void testCustomerRetrieval() {
//        CustomerDAO dao = new CustomerDAO();
//        List<Customer> customers = dao.getAllCustomers();
//        Assert.assertTrue(customers.size() > 0, "Customer list should not be empty");
//    }
//
//    @Test
//    public void testCustomerRegistration() {
//        CustomerDAO dao = new CustomerDAO();
//        Customer newCustomer = new Customer(0, "Test User", "9999999999", "testuser@example.com", 500.0);
//        boolean result = dao.registerCustomer(newCustomer);
//        Assert.assertTrue(result, "Customer registration should succeed");
//    }
//}
