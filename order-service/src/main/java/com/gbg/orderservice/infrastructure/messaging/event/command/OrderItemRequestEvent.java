package com.gbg.orderservice.infrastructure.messaging.event.command;

import com.gbg.orderservice.domain.entity.OrderItem;
import java.util.UUID;

public record OrderItemRequestEvent(
    UUID productId,
    UUID itemPromotionId,
    Integer stock
) {

    public static OrderItemRequestEvent from(OrderItem item) {
        return new OrderItemRequestEvent(
            item.getProductId(),
            item.getItemPromotionId(),
            item.getStock()
        );
    }

}
