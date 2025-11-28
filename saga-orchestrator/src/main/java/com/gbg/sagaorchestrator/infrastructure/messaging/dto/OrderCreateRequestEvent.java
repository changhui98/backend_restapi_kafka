package com.gbg.sagaorchestrator.infrastructure.messaging.dto;

import java.util.UUID;

public record OrderCreateRequestEvent(
    UUID orderId,
    UUID userId,
    UUID productId,
    Integer qty,
    String recipientName,
    String recipientPhone,
    String zipcode,
    String address1,
    String address2,
    String deliveryMessage
){

}
