package com.gbg.orderservice.presentiation.dto.request;

public record OrderCreateRequest(

    String productCode,
    int qty,
    int unitPrice
) {

}
