package com.gbg.userservice.presentation.dto.response;

import java.util.UUID;
import lombok.Data;

@Data
public class OrderResponse {

    private UUID orderId;
    private String productCode;
    private int qty;
    private int unitPrice;
    private int totalPrice;

}
