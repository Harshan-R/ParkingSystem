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

            if (customerService.hasOverdueSessions(customer.getId())) {
                System.out.println(" Dear " + customer.getName() + ", you have overdue parking sessions!");
            }

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
                    if (customer.getBalance() < 50.0) {
                        System.out.println("Insufficient balance. Minimum ₹50 required to start parking.");
                        break;
                    }

                    System.out.println("Your registered vehicles:");
                    List<Vehicle> vehicles = customerService.getVehiclesByCustomer(customer.getId());
                    vehicles.forEach(v -> System.out.println(v.getNumberPlate() + " (" + v.getVehicleType() + ")"));

                    System.out.print("Enter number plate to park: ");
                    String numberPlate = scanner.nextLine();
                    Vehicle selectedVehicle = customerService.getVehicleByPlate(numberPlate);

                    if (selectedVehicle == null || selectedVehicle.getCustomerId() != customer.getId()) {
                        System.out.println("Vehicle not found or not owned by you.");
                        break;
                    }

                    List<ParkingSlot> available = customerService.getAvailableSlots(selectedVehicle.getVehicleType());
                    if (available.isEmpty()) {
                        System.out.println("No slots available for " + selectedVehicle.getVehicleType());
                    } else {
                        ParkingSlot slot = available.get(0);
                        ParkingSession session = new ParkingSession(0, selectedVehicle.getId(), slot.getId(), new Timestamp(System.currentTimeMillis()), null, 0.0);
                        customerService.startParkingSession(session);
                        System.out.println("Parking started at slot " + slot.getId());
                    }
                    break;

                case 5:
                    System.out.print("Enter number plate to end session: ");
                    String plateToEnd = scanner.nextLine();
                    Vehicle vehicleToEnd = customerService.getVehicleByPlate(plateToEnd);

                    if (vehicleToEnd == null || vehicleToEnd.getCustomerId() != customer.getId()) {
                        System.out.println("Vehicle not found or not owned by you.");
                        break;
                    }

                    ParkingSession activeSession = customerService.getActiveSessionByVehicle(vehicleToEnd.getId());
                    if (activeSession == null) {
                        System.out.println("No active session found for this vehicle.");
                        break;
                    }

                    double charge = customerService.calculateCharges(activeSession.getEntryTime(), vehicleToEnd.getVehicleType());
                    customerService.endParkingSession(activeSession.getId(), activeSession.getSlotId(), charge);
                    System.out.println("Parking ended. Charges: ₹" + charge);
                    break;

                case 6:
                    List<Payment> payments = customerService.getPaymentsByCustomer(customer.getId());
                    payments.forEach(p -> System.out.println("Payment ID: " + p.getId() + ", Amount: ₹" + p.getAmount() + ", Type: " + p.getType()));
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
