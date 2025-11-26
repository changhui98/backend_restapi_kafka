package com.gbg.userservice.presentation.dto.request;

import java.util.UUID;

public record ValidatorRequest(
    UUID orderId,
    UUID userId,
    String request,
    String message
) {

}
