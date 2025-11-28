package com.gbg.sagaorchestrator.infrastructure.repository;

import com.gbg.sagaorchestrator.domain.entity.SagaState;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaOrderStateJpaRepository extends JpaRepository<SagaState, UUID> {

    SagaState findByOrderId(UUID orderId);
}
