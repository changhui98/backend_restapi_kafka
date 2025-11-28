package com.gbg.sagaorchestrator.infrastructure.messaging.event.request;

import java.util.UUID;

public record UserValidateRequestEvent(
    UUID orderId,
    UUID userId,
    String request,
    String message
) {

}
