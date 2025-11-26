package com.gbg.userservice.presentation.dto.response;

import java.util.UUID;

public record UserValidatorEvent(
    UUID orderId,
    UUID userId
) {

}
