package com.gbg.orderservice.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.orderservice.application.service.OrderService;
import com.gbg.orderservice.infrastructure.messaging.event.result.OrderCreateFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @KafkaListener(topics = "order.create.failed")
    public void failed(String message) {
        try {

            log.info("failed to create order {}", message);
            OrderCreateFailedEvent event = objectMapper.readValue(message,
                OrderCreateFailedEvent.class);

            orderService.orderFailed(event);
        } catch (JsonProcessingException e) {

            log.error("failed to create order {}", message, e);
        }
    }

}
