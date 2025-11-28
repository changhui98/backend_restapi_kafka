package com.gbg.orderservice.infrastructre.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.orderservice.infrastructre.messaging.dto.SagaStartedEvent;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(SagaStartedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order.create.request", message);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Json 역직렬화 실패 " + e.getMessage());
        }
    }

}
