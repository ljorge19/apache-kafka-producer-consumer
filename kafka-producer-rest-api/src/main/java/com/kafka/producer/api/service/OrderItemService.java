package com.kafka.producer.api.service;


import com.kafka.producer.api.config.kafka.KafkaProducerProperties;
import com.kafka.producer.api.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemService.class.getName());

    private KafkaProducerProperties kafkaProducerProperties;

    private final KafkaTemplate<String, Object> kafkaProducerTemplate;

    public OrderItemService(KafkaTemplate<String, Object> kafkaProducerTemplate, KafkaProducerProperties kafkaProducerProperties) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    @Transactional
    public CustomerDTO save(CustomerDTO orderItem){

        CustomerDTO resultOrderItem = null;
        try {

            orderItem.setId(1);
            String topic = kafkaProducerProperties.getTopicName();

            CompletableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(topic, orderItem.getId().toString(), orderItem);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    LOGGER.info("The record with key : {}, value : {} is produced successfully to offset {}",
                            result.getProducerRecord().key(), result.getProducerRecord().value(),
                            result.getRecordMetadata().offset());
                }
                else {
                    LOGGER.error("The record with key: {}, value: {} cannot be processed! caused by {}",
                            result.getProducerRecord().key(), result.getProducerRecord().value(),
                            ex.getMessage());
                }
            });

            //resultOrderItem = orderItemRepository.save(resultOrderItem);
        }catch(Exception ex) {
            LOGGER.error("Kafka producer send message exception details : {}", ex.getCause().getMessage());
        }

        return resultOrderItem;
    }
}