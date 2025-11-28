package com.gbg.sagaorchestrator.application.service;

import com.gbg.sagaorchestrator.domain.entity.SagaState;
import com.gbg.sagaorchestrator.domain.entity.SagaStatus;
import com.gbg.sagaorchestrator.infrastructure.messaging.dto.OrderCreateRequestEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.dto.UserValidatorEvent;
import com.gbg.sagaorchestrator.infrastructure.messaging.producer.SagaProducer;
import com.gbg.sagaorchestrator.infrastructure.repository.SagaOrderStateJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaOrderService {

    private final SagaOrderStateJpaRepository sagaOrderStateJpaRepository;
    private final SagaProducer sagaProducer;


    public void sagaProcess(OrderCreateRequestEvent event) {

        SagaState sagaState = SagaState.of(
            event.orderId(),
            "order create start",
            SagaStatus.SUCCESS,
            "success"
        );

        sagaOrderStateJpaRepository.save(sagaState);

        sagaProducer.sendUserService(new  UserValidatorEvent(event.orderId(), event.userId()));
    }

}
