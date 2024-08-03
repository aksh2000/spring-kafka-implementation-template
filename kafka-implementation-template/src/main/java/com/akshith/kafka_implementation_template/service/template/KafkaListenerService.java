package com.akshith.kafka_implementation_template.service.template;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * Abstract class that provides a template for Kafka listener services.
 * <p>
 * This class is responsible for receiving events from a Kafka topic, processing them, and handling any errors.
 * It provides a template method for executing the processing of events, including validation and error handling.
 * </p>
 *
 * @param <T> the type of the event being consumed by the listener
 */
@Slf4j
public abstract class KafkaListenerService<T> {

    /**
     * The Kafka topic that this listener service is subscribed to.
     * <p>
     * This field is initialized in the constructor with the topic name to which the service listens.
     * </p>
     */
    public final String TOPIC;

    protected KafkaListenerService(String topic) {
        TOPIC = topic;
    }

    /**
     * Executes the processing of the received event.
     * <p>
     * This method logs the received event, validates it, and if valid, processes it. If an exception occurs during processing,
     * it logs the error and leaves room for additional error handling or retry logic.
     * </p>
     *
     * @param event the event received from the Kafka topic
     */
    private void execute(T event) {
        log.warn("Received Event on Topic : {} with payload : {}", TOPIC, event);
        try {
            if (this.validate(event)) {
                log.debug("Attempting to process event : {} | {}", event, TOPIC);
                this.process(event);
                return;
            }
            log.warn("Event : {} | {} failed Validation check, Will be ignored", event, TOPIC);
        } catch (Exception e) {
            log.error("Error while processing event : {} | {}, error - {}", event, TOPIC, e.getMessage());
            // Re-Attempt Processing logic here | to be added later
        }
    }

    /**
     * Handles the event received from the Kafka topic.
     * <p>
     * This method is intended to be implemented by subclasses to define how the event should be processed.
     * </p>
     *
     * @param t the event to be processed
     */
    protected void listen(T t) {
        this.execute(t);
    }

    /**
     * Validates the received event before processing.
     * <p>
     * This method can be overridden by subclasses to perform custom validation. By default, it always returns {@code true}.
     * </p>
     *
     * @param t the event to be validated
     */
    protected abstract void process(T t);

    /**
     * Listens for events from the Kafka topic.
     * <p>
     * This method is intended to be implemented by subclasses to handle events received from the Kafka topic.
     * </p>
     *
     * @param t the event received from the Kafka topic
     */
    protected boolean validate(T t) {
        return true;
    }
}
