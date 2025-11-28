package com.gbg.userservice.infrastructure.kafka.event.request;

import java.util.UUID;

public record UserValidateEvent(
    UUID orderId,
    UUID userId
) {

}
