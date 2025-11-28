package com.gbg.sagaorchestrator.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.application.service.SagaOrderService;
import com.gbg.sagaorchestrator.domain.entity.SagaState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.repository.SagaOrderStateJpaRepository;
import com.gbg.sagaorchestrator.infrastructure.messaging.dto.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.dto.UserValidator;
import com.gbg.sagaorchestrator.infrastructure.messaging.dto.UserValidatorEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.producer.SagaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SagaConsumer {

    private final SagaProducer userProducer;
    private final ObjectMapper objectMapper;
    private final SagaOrderStateJpaRepository sagaOrderStateJpaRepository;
    private final SagaOrderService sagaOrderService;

    @KafkaListener(topics = "order.create.request")
    @Transactional
    public void onOrderCreate(String message) throws Exception {
        try {
            OrderCreateRequestEvent event =
                objectMapper.readValue(message, OrderCreateRequestEvent.class);

            sagaOrderService.sagaProcess(event);
            log.info("Order Service -> saga success {}", event);
        } catch (JsonProcessingException e) {

            log.error("Order Service -> saga fail {}", message, e);
        }
    }

    @KafkaListener(topics = "user.validate.response")
    @Transactional
    public void onUserValidateResponse(String message) throws Exception {
        log.info("5. 유저 서비스 -> 사가 오케스트레이션 메시지 받기 성공");
        UserValidator event = objectMapper.readValue(message, UserValidator.class);

        log.info("유저서비스 -> 사가 오케스트레이션 토픽 메시지 전달 성공");

        log.info("Received UserValidatorRequestEvent: {}", event);

        SagaState sagaState = sagaOrderStateJpaRepository.findByOrderId(event.orderId());

        if (event.request().equalsIgnoreCase("success")) {
            sagaState.setStatus(SagaStatus.USER_VALIDATED);
        } else {
            // 보상 트랜 잭션 실행
            sagaState.setStatus(SagaStatus.FAILED);
        }

        sagaOrderStateJpaRepository.save(sagaState);
    }

}
