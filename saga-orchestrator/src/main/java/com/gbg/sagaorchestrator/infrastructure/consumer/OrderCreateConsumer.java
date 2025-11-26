package com.gbg.sagaorchestrator.infrastructure.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.domain.entity.SagaOrderState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.repository.SagaOrderStateJpaRepository;
import com.gbg.sagaorchestrator.presentation.dto.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.presentation.dto.UserValidator;
import com.gbg.sagaorchestrator.presentation.dto.UserValidatorRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.producer.UserValidationProducer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreateConsumer {

    private final UserValidationProducer userProducer;
    private final ObjectMapper objectMapper;
    private final SagaOrderStateJpaRepository sagaOrderStateJpaRepository;

    @KafkaListener(topics = "order.create.request")
    @Transactional
    public void onOrderCreate(String message) throws Exception {
        log.info("1. 오더 서비스 -> 사가 오케스트레이션 메시지 받기 성공");
        SagaOrderState sagaOrderState = new SagaOrderState();

        OrderCreateRequestEvent event = objectMapper.readValue(message, OrderCreateRequestEvent.class);
        log.info("Received OrderCreateRequestEvent: {}", event);
        log.info("create order UserId : {}", event.userId());
        sagaOrderState.setOrderId(event.orderId());
        sagaOrderState.setStatus(SagaStatus.PENDING);
        sagaOrderStateJpaRepository.save(sagaOrderState);


        // 유저 검증 요청
        userProducer.sendUserValidation(new UserValidatorRequestEvent(event.orderId(), event.userId()));

    }

    @KafkaListener(topics = "user.validate.response")
    @Transactional
    public void onUserValidateResponse(String message) throws Exception {
        log.info("5. 유저 서비스 -> 사가 오케스트레이션 메시지 받기 성공");
        UserValidator event = objectMapper.readValue(message, UserValidator.class);

        log.info("유저서비스 -> 사가 오케스트레이션 토픽 메시지 전달 성공");

        log.info("Received UserValidatorRequestEvent: {}", event);

        SagaOrderState sagaOrderState = sagaOrderStateJpaRepository.findByOrderId(event.orderId());

        if (event.request().equalsIgnoreCase("success")) {
            sagaOrderState.setStatus(SagaStatus.USER_VALIDATED);
        } else {
            // 보상 트랜 잭션 실행
            sagaOrderState.setStatus(SagaStatus.FAILED);
        }

        sagaOrderStateJpaRepository.save(sagaOrderState);
    }

}
