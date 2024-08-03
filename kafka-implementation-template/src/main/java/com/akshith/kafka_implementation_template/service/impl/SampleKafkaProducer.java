package com.akshith.kafka_implementation_template.service.impl;

import com.akshith.kafka_implementation_template.service.template.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * A sample Kafka producer implementation for producing messages to a specific Kafka topic.
 * It extends the generic {@link KafkaProducerService} to provide a concrete implementation
 * for producing messages.
 */
@Slf4j
@Component
public class SampleKafkaProducer extends KafkaProducerService<Map<String, Object>> {

    /**
     * The Kafka topic to which messages will be produced.
     */
    private static final String TOPIC = "sample.kafka.topic.to.produce.on";

    /**
     * Constructs a {@link SampleKafkaProducer} with the specified Kafka bootstrap servers and topic.
     *
     * @param bootstrapServers The Kafka bootstrap servers URL, injected from application properties.
     */
    protected SampleKafkaProducer(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        super(bootstrapServers, TOPIC);
    }

    /**
     * Produces a message to the Kafka topic defined by {@link #TOPIC}.
     * Logs a warning message indicating the attempt to produce a message.
     *
     * @param message The message to produce, represented as a map of key-value pairs.
     */
    @Override
    public void produce(Map<String, Object> message) {
        log.warn("Attempting to produce a message");
        super.produce(message);
    }
}
