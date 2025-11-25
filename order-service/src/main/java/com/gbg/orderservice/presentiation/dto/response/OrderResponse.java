package com.gbg.orderservice.presentiation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gbg.orderservice.domain.entity.Order;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    private UUID orderId;
    private String productCode;
    private int qty;
    private int unitPrice;
    private int totalPrice;

    public static OrderResponse fromEntity(Order order) {
        OrderResponse response = new OrderResponse();
        response.orderId = order.getOrderId();
        response.productCode = order.getProductCode();
        response.qty = order.getQty();
        response.unitPrice = order.getUnitPrice();
        response.totalPrice = order.getTotalPrice();
        return response;
    }

}
