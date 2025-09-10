package com.example.dashboard;

import com.example.model.*;
import com.example.service.AdminService;

import java.util.List;
import java.util.Scanner;

public class AdminDashboard {

    private static AdminService adminService = new AdminService();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Vehicles");
            System.out.println("3. View All Parking Slots");
            System.out.println("4. View All Parking Sessions");
            System.out.println("5. View All Payments");
            System.out.println("6. View Top-Up Requests");
            System.out.println("7. Approve Top-Up Request");
            System.out.println("8. Deny Top-Up Request");
            System.out.println("9. Add Parking Slot");
            System.out.println("10. Change Slot Status");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    List<Customer> customers = adminService.viewAllCustomers();
                    customers.forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
                    break;
                case 2:
                    List<Vehicle> vehicles = adminService.viewAllVehicles();
                    vehicles.forEach(v -> System.out.println(v.getId() + " - " + v.getNumberPlate()));
                    break;
                case 3:
                    List<ParkingSlot> slots = adminService.viewAllSlots();
                    slots.forEach(s -> System.out.println(s.getId() + " - " + s.getStatus()));
                    break;
                case 4:
                    List<ParkingSession> sessions = adminService.viewAllSessions();
                    sessions.forEach(s -> System.out.println("Session ID: " + s.getId() + ", Slot: " + s.getSlotId()));
                    break;
                case 5:
                    List<Payment> payments = adminService.viewAllPayments();
                    payments.forEach(p -> System.out.println("Payment ID: " + p.getId() + ", Amount: ₹" + p.getAmount()));
                    break;
                case 6:
                    List<TopUpRequest> requests = adminService.viewAllTopUpRequests();
                    requests.forEach(r -> System.out.println("Request ID: " + r.getId() + ", Status: " + r.getStatus()));
                    break;
                case 7:
                    System.out.print("Enter Request ID to approve: ");
                    int approveId = scanner.nextInt();
                    adminService.approveTopUp(approveId);
                    break;
                case 8:
                    System.out.print("Enter Request ID to deny: ");
                    int denyId = scanner.nextInt();
                    adminService.denyTopUp(denyId);
                    break;
                case 9:
                    System.out.print("Enter Slot ID: ");
                    String slotId = scanner.nextLine();
                    System.out.print("Enter Vehicle Type (TWO_WHEELER/FOUR_WHEELER/SIX_WHEELER): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter Status (FREE/OCCUPIED/MAINTENANCE): ");
                    String status = scanner.nextLine();
                    ParkingSlot slot = new ParkingSlot(slotId, type, status);
                    adminService.addParkingSlot(slot);
                    break;
                case 10:
                    System.out.print("Enter Slot ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine();
                    adminService.changeSlotStatus(updateId, newStatus);
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
