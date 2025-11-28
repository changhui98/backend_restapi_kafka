package com.gbg.orderservice.infrastructure.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.orderservice.infrastructure.messaging.event.command.OrderCreateRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(OrderCreateRequestEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(
                "order.create.request",
                message);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Json 역직렬화 실패 " + e.getMessage());
        }
    }

}
