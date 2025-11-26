package com.gbg.sagaorchestrator.presentation.dto;

import java.util.UUID;

public record UserValidatorRequestEvent(
    UUID orderId,
    UUID userId
) {

}
