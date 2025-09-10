package com.example.stream;

import com.example.model.Payment;
import com.example.model.ParkingSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamUtils {

    // 🔹 1. Summarize payments by type (TOPUP, PARKING_FEE, PENALTY)
    public static Map<String, Double> summarizePaymentsByType(List<Payment> payments) {
        return payments.stream()
                .collect(Collectors.groupingBy(Payment::getType,
                        Collectors.summingDouble(Payment::getAmount)));
    }

    // 🔹 2. Top customers by total payment amount
    public static Map<Integer, Double> topCustomersByPayment(List<Payment> payments) {
        return payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomerId,
                        Collectors.summingDouble(Payment::getAmount)));
    }

    // 🔹 3. Count overdue sessions (exitTime is null)
    public static long countOverdueSessions(List<ParkingSession> sessions) {
        return sessions.stream()
                .filter(s -> s.getExitTime() == null)
                .count();
    }
}
