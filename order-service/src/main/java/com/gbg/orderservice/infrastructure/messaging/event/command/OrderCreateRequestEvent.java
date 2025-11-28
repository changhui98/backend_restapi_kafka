package com.gbg.orderservice.infrastructure.messaging.event.command;

import com.gbg.orderservice.domain.entity.Order;
import java.util.List;
import java.util.UUID;

public record OrderCreateRequestEvent(
    UUID orderId,
    UUID userId,
    List<OrderItemRequestEvent> items
) {

    public static OrderCreateRequestEvent from(Order order) {
        return new OrderCreateRequestEvent(
            order.getOrderId(),
            order.getUserId(),
            order.getItems().stream()
                .map(OrderItemRequestEvent::from)
                .toList()
        );
    }
}
