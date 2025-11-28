package com.gbg.sagaorchestrator.application.service;

import com.gbg.sagaorchestrator.domain.entity.SagaState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.outcome.OrderCreateFailedEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.request.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.command.UserValidateCommand;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.result.UserValidateFailEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.result.UserValidateSuccessEvent;
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
            "order-create-join",
            SagaStatus.SUCCESS,
            "success"
        );

        repository.save(sagaState);

        sagaProducer.sendUserValidate(new UserValidateCommand(event.orderId(), event.userId()));
    }

    public void userValidateSuccess(UserValidateSuccessEvent event) {

        SagaState saga = repository.findByOrderId(event.orderId());
        saga.updateStep("user-validate");
        saga.updateStatus(SagaStatus.USER_VALIDATED);
        saga.updateDetail("user-validated-successfully");
    }

    @Transactional
    public void userValidateFail(UserValidateFailEvent event) {

        SagaState saga = repository.findByOrderId(event.orderId());
        saga.updateStep("user-validate-failed");
        saga.updateStatus(SagaStatus.USER_VALIDATED_FAILED);
        saga.updateDetail(event.message());

        sagaProducer.sendOrderFailed(new OrderCreateFailedEvent(
            saga.getId(),
            saga.getOrderId(),
            "USER_NOT_FOUND",
            "SAGA001",
            "USER-SERVICE"
        ));
    }
}
