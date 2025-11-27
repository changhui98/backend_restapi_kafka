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
    private UUID userId;
    private UUID couponId;
    private Integer originalAmount;
    private Integer finalAmount;
    private Integer discountAmount;
    private OrderStatus orderStatus;
    private String paymentMethod;
    private String recipientName;
    private String recipientPhone;
    private String zipcode;
    private String address1;
    private String address2;
    private String deliveryMessage;

}
