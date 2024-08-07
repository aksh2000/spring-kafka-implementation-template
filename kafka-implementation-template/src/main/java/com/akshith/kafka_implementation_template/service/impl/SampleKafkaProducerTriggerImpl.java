package com.akshith.kafka_implementation_template.service.impl;

import com.akshith.kafka_implementation_template.service.config.KafkaProducerStore;
import com.akshith.kafka_implementation_template.service.template.KafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementation class for triggering Kafka producers to produce messages to Kafka topics.
 * <p>
 * This service is responsible for triggering the Kafka producer to send messages to a specific Kafka topic
 * based on business logic. It uses the Kafka producer bean configured for the topic {@link KafkaProducerStore#SAMPLE_TOPIC_ONE}.
 * </p>
 */
@Service
public class SampleKafkaProducerTriggerImpl {

    /**
     * The Kafka producer used to send messages to the Kafka topic specified by {@link KafkaProducerStore#SAMPLE_TOPIC_ONE}.
     * <p>
     * This producer is injected via constructor injection with the {@link Qualifier} annotation to ensure the correct
     * bean is used.
     * </p>
     */
    private final KafkaProducer<Map<String, Object>> sampleTopicOneProducer;

    /**
     * Constructs an instance of {@link SampleKafkaProducerTriggerImpl} with the specified Kafka producer.
     * <p>
     * The producer is injected through the constructor with the {@link Qualifier} annotation to specify the correct
     * bean to be injected.
     * </p>
     *
     * @param sampleTopicOneProducer the Kafka producer bean configured for the topic {@link KafkaProducerStore#SAMPLE_TOPIC_ONE}.
     */
    public SampleKafkaProducerTriggerImpl(@Qualifier(KafkaProducerStore.SAMPLE_TOPIC_ONE) KafkaProducer<Map<String, Object>> sampleTopicOneProducer) {
        this.sampleTopicOneProducer = sampleTopicOneProducer;
    }


    /**
     * Triggers the Kafka producer to send a sample message to the Kafka topic.
     * <p>
     * This method contains the business logic for producing a message to the Kafka topic. The message is a map with a
     * sample key-value pair.
     * </p>
     */
    public void dummyTrigger() {
        // Business Logic Here
        sampleTopicOneProducer.produce(Map.of("Sample_Key", true));
    }
}
