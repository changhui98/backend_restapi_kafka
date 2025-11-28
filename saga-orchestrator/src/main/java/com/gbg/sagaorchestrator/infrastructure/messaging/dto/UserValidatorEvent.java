package com.gbg.sagaorchestrator.infrastructure.messaging.dto;

import java.util.UUID;

public record UserValidatorEvent(
    UUID orderId,
    UUID userId
) {

}
