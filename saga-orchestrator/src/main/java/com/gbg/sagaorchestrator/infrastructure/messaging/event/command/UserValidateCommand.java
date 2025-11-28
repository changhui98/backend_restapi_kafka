package com.gbg.sagaorchestrator.infrastructure.messaging.event.command;

import java.util.UUID;

public record UserValidateCommand(
    UUID orderId,
    UUID userId
) {

}
