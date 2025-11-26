package com.gbg.orderservice.presentiation.dto.request;

public record OrderCreateRequest(

    String productCode,
    Integer qty,
    Integer unitPrice
) {

}
