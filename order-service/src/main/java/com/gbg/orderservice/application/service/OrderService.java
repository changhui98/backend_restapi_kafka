package com.gbg.orderservice.application.service;

import com.gbg.orderservice.domain.entity.Order;
import com.gbg.orderservice.domain.entity.OrderItem;
import com.gbg.orderservice.domain.entity.OrderItemStatus;
import com.gbg.orderservice.domain.entity.OrderStatus;
import com.gbg.orderservice.infrastructure.messaging.event.command.OrderCreateRequestEvent;
import com.gbg.orderservice.infrastructure.messaging.event.result.OrderCreateFailedEvent;
import com.gbg.orderservice.infrastructure.messaging.producer.OrderProducer;
import com.gbg.orderservice.infrastructure.repoisotry.OrderJpaRepository;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest.OrderItemCreateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderProducer orderProducer;

    @Transactional
    public UUID createOrder(UUID userId, OrderCreateRequest req) {

        // 1. Create Order
        Order order = Order.of(
            userId,
            req.couponId(),
            null,
            null,
            null,
            req.paymentMethod(),
            req.recipientName(),
            req.recipientPhone(),
            req.zipcode(),
            req.address1(),
            req.address2(),
            req.deliveryMessage()
        );

        // 2. OrderItem Create Then Add Order
        for (OrderItemCreateRequest item : req.items()) {
            OrderItem orderItem = OrderItem.of(
                item.productId(),
                item.itemPromotionId(),
                item.stock(),
                null,
                null,
                null
            );
            order.addItem(orderItem);
        }

        // 3. Order Plus OrderItem Save
        Order saveOrder = orderJpaRepository.save(order);

        // 4. event push -> saga-orchestrator
        OrderCreateRequestEvent event = OrderCreateRequestEvent.from(order);
        orderProducer.send(event);

        return saveOrder.getOrderId();
    }

    @Transactional
    public void orderFailed(OrderCreateFailedEvent event) {
        Order order = orderJpaRepository.findWithItemsById(event.orderId()).orElseThrow(
            () -> new RuntimeException("order id not found")
        );
        order.updateStatus(OrderStatus.FAILED);
        order.getItems().forEach(orderItem -> {
            orderItem.updateStatus(OrderItemStatus.FAILED);
        });

    }

}
