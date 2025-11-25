package com.gbg.productservice.presentation.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private String productCode;
    private int qty;
    private int unitPrice;

}
