package com.akshith.kafka_implementation_template.service.config;

import com.akshith.kafka_implementation_template.service.template.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * A sample implementation of a Kafka producer for producing messages to specific Kafka topics.
 * <p>
 * This class extends the generic {@link KafkaProducer} to provide concrete implementations for producing messages
 * to predefined Kafka topics. It demonstrates how to configure Kafka producers with different topics and custom logic.
 * </p>
 * <p>
 * The class is annotated with {@link Component} to allow Spring to detect it as a bean and manage its lifecycle.
 * </p>
 */
@Slf4j
@Component
public class KafkaProducerStore {

    /**
     * The Kafka bootstrap servers URL(s) used to connect to the Kafka cluster.
     * <p>
     * This value is injected from the application properties using {@link Value} annotation.
     * </p>
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    /**
     * The name of the Kafka topic's for which the producer is configured.
     * <p>
     * These are constants representing the Kafka topic's name.
     * </p>
     */
    public static final String SAMPLE_TOPIC_ONE = "sample.topic.1";
    public static final String SAMPLE_TOPIC_TWO = "sample.topic.1";


    /**
     * Creates a {@link KafkaProducer} bean for the first Kafka topic.
     * <p>
     * This method configures a Kafka producer for the topic defined by {@link #SAMPLE_TOPIC_ONE} using the provided
     * bootstrap servers URL.
     * </p>
     *
     * @return a {@link KafkaProducer} instance configured for {@link #SAMPLE_TOPIC_ONE}.
     */
    @Bean(SAMPLE_TOPIC_ONE)
    public KafkaProducer<Map<String, Object>> buildSampleProducer() {
        return new KafkaProducer<Map<String, Object>>(bootstrapServers, SAMPLE_TOPIC_ONE) {
        };
    }

    /**
     * Creates a {@link KafkaProducer} bean for the second Kafka topic with custom logic.
     * <p>
     * This method configures a Kafka producer for the topic defined by {@link #SAMPLE_TOPIC_TWO} using the provided
     * bootstrap servers URL.
     * </p>
     *
     * @return a {@link KafkaProducer} instance configured for {@link #SAMPLE_TOPIC_TWO}.
     */
    @Bean(SAMPLE_TOPIC_TWO)
    public KafkaProducer<Map<String, Object>> buildSampleProducer2() {
        return new KafkaProducer<Map<String, Object>>(bootstrapServers, SAMPLE_TOPIC_TWO) {
            @Override
            protected void handleFailure(Throwable ex, Map<String, Object> message) {
                log.error("Custom Failure Handling is being done here for error : {}", ex.getMessage());
                super.handleFailure(ex, message);
            }
        };
    }
}
