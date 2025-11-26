package com.gbg.sagaorchestrator.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.dto.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.dto.UserValidatorRequestEvent;
import com.gbg.sagaorchestrator.producer.UserValidationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreateConsumer {

    private final UserValidationProducer userProducer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.create.request", groupId = "saga-orchestrator")
    public void onOrderCreate(String message) throws Exception {

        OrderCreateRequestEvent event = objectMapper.readValue(message, OrderCreateRequestEvent.class);
        log.info("Received OrderCreateRequestEvent: {}", event);
        log.info("create order UserId : {}", event.userId());

        // 유저 검증 요청
        userProducer.sendUserValidation(new UserValidatorRequestEvent(event.orderId(), event.userId()));
    }

}
