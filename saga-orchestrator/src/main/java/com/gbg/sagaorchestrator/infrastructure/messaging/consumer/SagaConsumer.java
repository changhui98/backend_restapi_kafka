package com.gbg.sagaorchestrator.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.application.service.SagaService;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.result.UserValidateFailEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.result.UserValidateSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SagaConsumer {

    private final ObjectMapper objectMapper;
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

    @KafkaListener(topics = "user.validate.success")
    public void onUserValidateSuccess(String message) throws Exception {
        try {
            UserValidateSuccessEvent event =
                objectMapper.readValue(message, UserValidateSuccessEvent.class);

            sagaService.userValidateSuccess(event);
            log.info("User Service -> saga success {}", event);
        } catch (JsonProcessingException e) {

            log.error("User Service -> saga fail {}", message, e);
        }
    }

    @KafkaListener(topics = "user-validate-fail")
    public void onUserValidateFail(String message) throws Exception {
        try {
            UserValidateFailEvent event =
                objectMapper.readValue(message, UserValidateFailEvent.class);

            sagaService.userValidateFail(event);
        } catch (JsonProcessingException e) {

            log.error("User Service -> saga fail {}", message, e);
        }
    }

}
