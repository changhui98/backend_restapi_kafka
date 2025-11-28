package com.gbg.userservice.infrastructure.kafka.event.command;

import java.util.UUID;

public record UserValidateSuccessEvent(
    UUID orderId,
    UUID userId
) implements UserValidateEventBase {

}
