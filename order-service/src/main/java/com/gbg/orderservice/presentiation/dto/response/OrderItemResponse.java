package com.gbg.orderservice.presentiation.dto.response;

import com.gbg.orderservice.domain.entity.OrderItemStatus;
import java.util.UUID;

public record OrderItemResponse(
    UUID orderItemId,
    UUID productId,
    UUID itemPromotionId,
    Integer stock,
    Integer unitPrice,
    Integer finalPrice,
    Integer itemDiscountAmount,
    OrderItemStatus orderItemStatus
) {

}
