package com.gbg.sagaorchestrator.infrastructure.messaging.dto;

import java.util.UUID;

public record UserValidator(
    UUID orderId,
    UUID userId,
    String request,
    String message
) {

}
