package com.example;

import com.example.model.Customer;
import com.example.service.LoginService;
import com.example.service.CustomerService;
import com.example.dashboard.AdminDashboard;
import com.example.dashboard.CustomerDashboard;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();
        CustomerService customerService = new CustomerService();
        
        String asciiArt;
		try {
			asciiArt = FigletFont.convertOneLine("RHN - Parking Lot");
			 System.out.println(asciiArt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        while (true) {
            System.out.println("\n=== Welcome to Parking System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Existing Customer Login");
            System.out.println("3. New Customer Registration");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter admin username: ");
                    String adminUser = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPass = scanner.nextLine();

                    if (loginService.isAdmin(adminUser, adminPass)) {
                        AdminDashboard.showMenu();
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;

                case "2":
                    System.out.print("Enter your email or phone number: ");
                    String input = scanner.nextLine();
                    Customer customer = loginService.authenticateCustomer(input);
                    if (customer != null) {
                        CustomerDashboard.showMenu(customer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case "3":
                    System.out.println("=== New Customer Registration ===");
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    Customer newCustomer = new Customer(0, name, phone, email, 1000.0);
                    boolean registered = customerService.registerNewCustomer(newCustomer);
                    if (registered) {
                        System.out.println("Registration successful! Please login as an existing customer.");
                    } else {
                        System.out.println("Registration failed. Try again.");
                    }
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
