package com.gbg.userservice.infrastructure.kafka.event.command;

import java.util.UUID;

public record UserValidateFailEvent(
    UUID orderId,
    UUID userId,
    String message
) implements UserValidateEventBase {

}
