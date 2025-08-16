package com.kafka.consumer.api.service.kafka.topic.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaOrderItemTopicListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaOrderItemTopicListenerService.class.getName());

    @KafkaListener(topics = "orders", groupId = "group_id")
    public void consumeFlexible(String message) {
        LOGGER.info("Raw message from Kafka = {}", message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(message, new TypeReference<Map<String, Object>>() {});
            LOGGER.info("Parsed as JSON Map: {}", json);
        } catch (Exception e) {
            LOGGER.warn("Message is not JSON, keeping as raw String");
        }
    }

    }