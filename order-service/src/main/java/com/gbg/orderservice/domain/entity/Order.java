package com.gbg.orderservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private String productCode;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private UUID userId;

    public static Order of(String productCode, Integer qty, Integer unitPrice, Integer totalPrice, UUID userId) {
        Order order = new Order();
        order.productCode = productCode;
        order.qty = qty;
        order.unitPrice = unitPrice;
        order.totalPrice = totalPrice;
        order.userId = userId;
        return order;
    }

    private Order(UUID orderId, String productCode, Integer qty, Integer unitPrice, Integer totalPrice,
        UUID userId) {
        this.orderId = orderId;
        this.productCode = productCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }
}
