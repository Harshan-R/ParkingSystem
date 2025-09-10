package com.example;

import com.example.model.Customer;
import com.example.service.LoginService;
import com.example.dashboard.AdminDashboard;
import com.example.dashboard.CustomerDashboard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();

        System.out.println("Welcome to Parking System");
        System.out.print("Enter username/email/phone: ");
        String username = scanner.nextLine();

        System.out.print("Enter password (for admin only): ");
        String password = scanner.nextLine();

        if (loginService.isAdmin(username, password)) {
            AdminDashboard.showMenu();
        } else {
            Customer customer = loginService.authenticateCustomer(username);
            if (customer != null) {
                CustomerDashboard.showMenu(customer);
            } else {
                System.out.println("Invalid login. Please try again.");
            }
        }

        scanner.close();
    }
}
