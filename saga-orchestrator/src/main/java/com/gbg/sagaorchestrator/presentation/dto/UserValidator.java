package com.gbg.sagaorchestrator.presentation.dto;

import java.util.UUID;

public record UserValidator(
    UUID orderId,
    UUID userId,
    String request,
    String message
) {

}
