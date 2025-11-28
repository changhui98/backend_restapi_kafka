package com.gbg.sagaorchestrator.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.application.service.SagaService;
import com.gbg.sagaorchestrator.domain.entity.SagaState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.repository.SagaOrderStateJpaRepository;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.UserValidateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.producer.SagaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SagaConsumer {

    private final SagaProducer userProducer;
    private final ObjectMapper objectMapper;
    private final SagaOrderStateJpaRepository sagaOrderStateJpaRepository;
    private final SagaService sagaService;

    @KafkaListener(topics = "order.create.request")
    public void onOrderCreate(String message) throws Exception {
        try {
            log.info("Join Saga Service : {}", message);
            OrderCreateRequestEvent event =
                objectMapper.readValue(message, OrderCreateRequestEvent.class);

            sagaService.sagaProcess(event);
            log.info("Order Service -> saga success {}", event);
        } catch (JsonProcessingException e) {

            log.error("Order Service -> saga fail {}", message, e);
        }
    }

    @KafkaListener(topics = "user.validate.response")
    public void onUserValidateResponse(String message) throws Exception {
        try {
            UserValidateRequestEvent event =
                objectMapper.readValue(message, UserValidateRequestEvent.class);

            sagaService.userProcess(event);
            log.info("User Service -> saga success {}", event);
        } catch (JsonProcessingException e) {

            log.error("User Service -> saga fail {}", message, e);
        }
    }

}
