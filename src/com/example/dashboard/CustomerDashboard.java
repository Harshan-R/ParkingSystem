package com.example.dashboard;

import com.example.model.*;
import com.example.service.CustomerService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class CustomerDashboard {

    private static CustomerService customerService = new CustomerService();

    public static void showMenu(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Customer Dashboard ---");
            System.out.println("Welcome, " + customer.getName());
            System.out.println("1. View Balance");
            System.out.println("2. Request Top-Up");
            System.out.println("3. Register Vehicle");
            System.out.println("4. Start Parking Session");
            System.out.println("5. End Parking Session");
            System.out.println("6. View Payment History");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Your balance: ₹" + customer.getBalance());
                    break;
                case 2:
                    System.out.print("Enter top-up amount: ");
                    double amount = scanner.nextDouble();
                    TopUpRequest request = new TopUpRequest(0, customer.getId(), amount, "PENDING", new Timestamp(System.currentTimeMillis()));
                    customerService.requestTopUp(request);
                    System.out.println("Top-up request submitted.");
                    break;
                case 3:
                    System.out.print("Enter vehicle type (TWO_WHEELER/FOUR_WHEELER/SIX_WHEELER): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter number plate (e.g., TN-01-AB-1234): ");
                    String plate = scanner.nextLine();
                    Vehicle vehicle = new Vehicle(0, customer.getId(), type, plate);
                    customerService.registerVehicle(vehicle);
                    System.out.println("Vehicle registered.");
                    break;
                case 4:
                    System.out.print("Enter vehicle ID to park: ");
                    int vehicleId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter vehicle type: ");
                    String vType = scanner.nextLine();
                    List<ParkingSlot> available = customerService.getAvailableSlots(vType);
                    if (available.isEmpty()) {
                        System.out.println("No slots available.");
                    } else {
                        ParkingSlot slot = available.get(0);
                        ParkingSession session = new ParkingSession(0, vehicleId, slot.getId(), new Timestamp(System.currentTimeMillis()), null, 0.0);
                        customerService.startParkingSession(session);
                        System.out.println("Parking started at slot " + slot.getId());
                    }
                    break;
                case 5:
                    System.out.print("Enter session ID to end: ");
                    int sessionId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter slot ID: ");
                    String slotId = scanner.nextLine();
                    System.out.print("Enter charges: ");
                    double charges = scanner.nextDouble();
                    customerService.endParkingSession(sessionId, slotId, charges);
                    System.out.println("Parking ended and payment recorded.");
                    break;
                case 6:
                    List<Payment> payments = customerService.getPaymentsByCustomer(customer.getId());
                    payments.forEach(p -> System.out.println("Payment ID: " + p.getId() + ", Amount: ₹" + p.getAmount()));
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
}
