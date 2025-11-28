package com.gbg.sagaorchestrator.infrastructure.messaging.event.request;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestEvent(
    UUID orderId,
    UUID userId,
    List<OrderItem> items
){

    public static record OrderItem(
        UUID productId,
        UUID itemPromotionId,
        Integer stock
    ) {

    }
}
