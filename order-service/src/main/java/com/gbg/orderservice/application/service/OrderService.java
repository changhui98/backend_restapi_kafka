package com.gbg.orderservice.application.service;

import com.gbg.orderservice.domain.entity.Order;
import com.gbg.orderservice.infrastructre.producer.OrderCreateProducer;
import com.gbg.orderservice.infrastructre.repoisotry.OrderJpaRepository;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequestEvent;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequestV2;
import com.gbg.orderservice.presentiation.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderCreateProducer orderCreateProducer;

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

    public void createOrderV2(UUID userId, OrderCreateRequestV2 dto) {

        OrderCreateRequestEvent event = new OrderCreateRequestEvent(
            UUID.randomUUID(),
            userId,
            dto.productId(),
            dto.qty(),
            dto.recipientName(),
            dto.recipientPhone(),
            dto.zipcode(),
            dto.address1(),
            dto.address2(),
            dto.deliveryMessage()
        );

        orderCreateProducer.sendOrderCreateEvent(event);
    }
}
