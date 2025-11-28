package com.gbg.sagaorchestrator.application.service;

import com.gbg.sagaorchestrator.domain.entity.SagaState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.outcome.OrderCreateFailedEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.command.UserValidateCommand;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.UserValidateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.producer.SagaProducer;
import com.gbg.sagaorchestrator.infrastructure.repository.SagaOrderStateJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaService {

    private final SagaOrderStateJpaRepository repository;
    private final SagaProducer sagaProducer;

    @Transactional
    public void sagaProcess(OrderCreateRequestEvent event) {

        if (event.orderId() == null) {
            // 주문 생성이 실패 했을 경우 예외 로직
        }

        SagaState sagaState = SagaState.of(
            event.orderId(),
            "order create join",
            SagaStatus.SUCCESS,
            "success"
        );

        repository.save(sagaState);

        sagaProducer.sendUserValidate(new UserValidateCommand(event.orderId(), event.userId()));
    }

    public void userProcess(UserValidateRequestEvent event) {

        SagaState saga = repository.findByOrderId(event.orderId());
        saga.updateStep("user validate");

        if (!event.request().equalsIgnoreCase("success")) {
            saga.updateStatus(SagaStatus.FAILED);
            sagaProducer.sendOrderFailed(new OrderCreateFailedEvent(
                saga.getId(),
                saga.getOrderId(),
                "사용자 검증 실패: 존재하지 않는 사용자입니다.",
                "USER_NOT_FOUND",
                "USER-SERVICE"
            ));
        }

        saga.updateStatus(SagaStatus.USER_VALIDATED);
    }

}
