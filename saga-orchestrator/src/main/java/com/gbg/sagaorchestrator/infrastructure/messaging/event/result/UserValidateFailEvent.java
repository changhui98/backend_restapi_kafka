package com.gbg.sagaorchestrator.infrastructure.messaging.event.result;

import java.util.UUID;

public record UserValidateFailEvent(
    UUID orderId,
    UUID userId,
    String message
) {

}
