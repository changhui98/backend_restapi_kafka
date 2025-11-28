package com.gbg.orderservice.domain.entity;

public enum OrderStatus {

    CREATED,
    WAITING_PAYMENT,
    FAILED,
    CANCELED,
    COMPLETED,
    USER_VALIDATE_FAILED
}
