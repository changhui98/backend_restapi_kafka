package com.gbg.orderservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItemId;
    private UUID productId;
    private UUID itemPromotionId;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;
    private Integer stock;
    private Integer unitPrice;
    private Integer finalPrice;
    private Integer itemDiscountAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void updateStatus(OrderItemStatus status) {
        this.status = status;
    }

    public static OrderItem of(
        UUID productId,
        UUID itemPromotionId,
        Integer stock,
        Integer unitPrice,
        Integer finalPrice,
        Integer itemDiscountAmount
    ) {
        OrderItem orderItem = new OrderItem();
        orderItem.productId = productId;
        orderItem.itemPromotionId = itemPromotionId;
        orderItem.stock = stock;
        orderItem.unitPrice = unitPrice;
        orderItem.finalPrice = finalPrice;
        orderItem.itemDiscountAmount = itemDiscountAmount;
        orderItem.status = OrderItemStatus.PENDING;

        return orderItem;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

}
