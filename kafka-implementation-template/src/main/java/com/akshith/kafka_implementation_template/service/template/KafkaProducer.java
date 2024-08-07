package com.akshith.kafka_implementation_template.service.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract class for Kafka producer services.
 * <p>
 * This class provides a template for producing messages to a Kafka topic. It handles Kafka configuration,
 * message sending, and error handling.
 * </p>
 *
 * @param <T> the type of the message being produced
 */
@Slf4j
public abstract class KafkaProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;
    private final String topic;

    protected KafkaProducer(String bootstrapServers, String topic) {
        this.topic = topic;
        this.kafkaTemplate = createKafkaTemplate(bootstrapServers);
    }

    /**
     * Creates a {@link KafkaTemplate} with the specified bootstrap servers.
     *
     * @param bootstrapServers the address of the Kafka brokers
     * @return a {@link KafkaTemplate} configured with the specified bootstrap servers
     */
    private KafkaTemplate<String, T> createKafkaTemplate(String bootstrapServers) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, T> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Sends a message to the Kafka topic.
     * <p>
     * This method logs the message being sent and its associated topic. It also handles the result of the send operation
     * and logs any errors encountered. On success, it logs the metadata of the sent message.
     * </p>
     *
     * @param message the message to be sent
     */
    private void sendMessage(T message) {
        log.warn("Triggering Event on {} with payload : {}", topic, message);
        CompletableFuture<SendResult<String, T>> completableFuture = kafkaTemplate.send(topic, message);
        completableFuture.whenComplete((result, ex) -> {
            if (null != ex) {
                log.error("Failed to trigger event : {} on topic: {} due to : {}", message, topic, ex.getMessage());
                handleFailure(ex, message);
            } else {
                if (null != result) {
                    var metadata = result.getRecordMetadata();
                    log.debug("Triggered Event on topic: {} partition:{} offset:{} with payload : {}", metadata.topic(), metadata.partition(), metadata.offset(), message);
                }
            }
        });
    }

    /**
     * Produces a message to the Kafka topic.
     * <p>
     * This method is asynchronous and invokes {@link #sendMessage(T)} to send the message. Subclasses can override this
     * method to add custom logic before or after sending the message.
     * </p>
     *
     * @param message the message to be produced
     */
    @Async
    public final void produce(T message) {
        this.sendMessage(message);
    }

    /**
     * Handles failures encountered while sending a message to the Kafka topic.
     * <p>
     * This method logs the failure. Subclasses can override this method to implement custom failure handling logic.
     * </p>
     *
     * @param ex      the exception encountered during message sending
     * @param message the message that failed to be sent
     */
    protected void handleFailure(Throwable ex, T message) {
        log.error("Unhandled Failure for topic : {} while sending payload : {} | ex : {}", topic, message, ex.getMessage());
    }
}
