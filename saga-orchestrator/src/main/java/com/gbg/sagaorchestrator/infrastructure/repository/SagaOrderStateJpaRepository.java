package com.gbg.sagaorchestrator.infrastructure.repository;

import com.gbg.sagaorchestrator.domain.entity.SagaOrderState;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaOrderStateJpaRepository extends JpaRepository<SagaOrderState, UUID> {

    SagaOrderState findByOrderId(UUID orderId);
}
