package com.gbg.orderservice.presentiation.dto.request;

import java.util.UUID;

public record OrderCreateRequestV2(
    UUID productId,
    Integer qty,
    String recipientName,
    String recipientPhone,
    String zipcode,
    String address1,
    String address2,
    String deliveryMessage
) {

}
