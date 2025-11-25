package com.gbg.orderservice.application.service;

import com.gbg.orderservice.domain.entity.Order;
import com.gbg.orderservice.infrastructre.repoisotry.OrderJpaRepository;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest;
import com.gbg.orderservice.presentiation.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    public UUID createOrder(UUID userId, OrderCreateRequest orderCreateRequest) {

        Order order = Order.of(orderCreateRequest.productCode(), orderCreateRequest.qty(),
            orderCreateRequest.unitPrice(),
            orderCreateRequest.qty() * orderCreateRequest.unitPrice(), userId);

        orderJpaRepository.save(order);

        return order.getOrderId();
    }

    public List<OrderResponse> orderList(UUID userId) {

        List<Order> orderList = orderJpaRepository.findByUserId(userId);

        return orderList.stream()
            .map(OrderResponse::fromEntity)
            .toList();
    }
}
