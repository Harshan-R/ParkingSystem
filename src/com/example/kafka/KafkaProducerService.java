package com.example.kafka;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class KafkaProducerService {

    private static final String TOPIC = "parking-events";
    private final Producer<String, String> producer;

    public KafkaProducerService() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Update if needed
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props); // ✅ Only create the producer here
    }

    public void sendEvent(String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, message);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Error sending event to Kafka:");
                exception.printStackTrace();
            } else {
                System.out.printf("Event sent to Kafka topic %s partition %d offset %d: %s%n",
                        metadata.topic(), metadata.partition(), metadata.offset(), message);
            }
        });
    }

    public void close() {
        producer.close();
    }
}
