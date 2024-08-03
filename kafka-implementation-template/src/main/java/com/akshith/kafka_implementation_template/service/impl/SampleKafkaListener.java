package com.akshith.kafka_implementation_template.service.impl;

import com.akshith.kafka_implementation_template.service.template.KafkaListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * A sample Kafka listener implementation that listens to messages from a specific Kafka topic.
 * It extends the generic {@link KafkaListenerService} to provide custom processing and validation logic.
 */
@Slf4j
@Component
public class SampleKafkaListener extends KafkaListenerService<Map<String, Object>> {

    /**
     * The Kafka topic this listener subscribes to.
     */
    private static final String TOPIC = "sample.kafka.topic.to.listen.on";

    /**
     * Constructs a {@link SampleKafkaListener} with a predefined Kafka topic.
     */
    protected SampleKafkaListener() {
        super(TOPIC);
    }

    /**
     * Listens for messages on the Kafka topic defined by {@link #TOPIC}.
     * This method is automatically invoked by Spring Kafka when a message is received.
     *
     * @param stringObjectMap The message received from Kafka, represented as a map of key-value pairs.
     */
    @KafkaListener(topics = SampleKafkaListener.TOPIC)
    @Override
    protected void listen(Map<String, Object> stringObjectMap) {
        super.listen(stringObjectMap);
    }

    /**
     * Processes the received message. This method is invoked after validation.
     * Subclasses should override this method to provide specific processing logic.
     *
     * @param stringObjectMap The message to process, represented as a map of key-value pairs.
     */
    @Override
    protected void process(Map<String, Object> stringObjectMap) {
        log.warn("Message is getting processed");
    }

    /**
     * Validates the received message. This method should be overridden to implement specific validation logic.
     *
     * @param stringObjectMap The message to validate, represented as a map of key-value pairs.
     * @return {@code true} if the message is valid, {@code false} otherwise.
     */
    @Override
    protected boolean validate(Map<String, Object> stringObjectMap) {
        return CollectionUtils.isEmpty(stringObjectMap);
    }
}
