package com.gbg.orderservice.infrastructre.messaging.dto;

import com.gbg.orderservice.domain.entity.Order;
import java.util.List;
import java.util.UUID;

public record SagaStartedEvent(
    UUID orderId,
    UUID userId,
    List<SagaOrderItem> items
) {

    public static SagaStartedEvent from(Order order) {
        return new SagaStartedEvent(
            order.getOrderId(),
            order.getUserId(),
            order.getItems().stream()
                .map(SagaOrderItem::from)
                .toList()
        );
    }
}
