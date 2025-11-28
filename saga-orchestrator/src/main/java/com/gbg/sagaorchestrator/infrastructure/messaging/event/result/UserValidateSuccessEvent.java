package com.gbg.sagaorchestrator.infrastructure.messaging.event.result;

import java.util.UUID;

public record UserValidateSuccessEvent(
    UUID orderId,
    UUID userId
) {

}
