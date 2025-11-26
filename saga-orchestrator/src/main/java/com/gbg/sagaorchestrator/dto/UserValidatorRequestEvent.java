package com.gbg.sagaorchestrator.dto;

import java.util.UUID;

public record UserValidatorRequestEvent(
    UUID orderId,
    UUID userId
) {

}
