
package com.example.stream;
import com.example.model.Payment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamUtils {

    public static Map<String, Double> summarizePaymentsByType(List<Payment> payments) {
        return payments.stream()
                .collect(Collectors.groupingBy(Payment::getType,
                        Collectors.summingDouble(Payment::getAmount)));
    }
}
