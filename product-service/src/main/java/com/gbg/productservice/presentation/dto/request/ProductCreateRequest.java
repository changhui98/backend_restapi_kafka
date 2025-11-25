package com.gbg.productservice.presentation.dto.request;

public record ProductCreateRequest(
    String productCode,
    int qty,
    int unitPrice
) {

}
