package com.gbg.orderservice.presentiation.dto.response;

import com.gbg.orderservice.domain.entity.OrderStatus;
import java.util.List;
import java.util.UUID;

public record OrderDetailResponse(
    UUID orderId,
    UUID userId,
    UUID couponId,
    Integer originalAmount,
    Integer finalAmount,
    Integer discountAmount,
    OrderStatus orderStatus,
    String paymentMethod,
    String recipientName,
    String recipientPhone,
    String zipcode,
    String address1,
    String address2,
    String deliveryMessage
) {

}
