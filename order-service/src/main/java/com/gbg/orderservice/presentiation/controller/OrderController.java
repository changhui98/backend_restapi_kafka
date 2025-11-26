package com.gbg.orderservice.presentiation.controller;

import com.gbg.orderservice.application.service.OrderService;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequestEvent;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequestV2;
import com.gbg.orderservice.presentiation.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<UUID> createOrder(
        @PathVariable UUID userId,
        @RequestBody OrderCreateRequest orderCreateRequest) {

        UUID orderId = orderService.createOrder(userId, orderCreateRequest);

        return ResponseEntity.ok().body(orderId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> orderList(
        @PathVariable("userId") UUID userId
    ) {
        List<OrderResponse> orderList = orderService.orderList(userId);

        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping("/{userId}/v2")
    public ResponseEntity<String> createOrderV2(
        @PathVariable UUID userId,
        @RequestBody OrderCreateRequestV2 dto
    ) {

        orderService.createOrderV2(userId, dto);

        return ResponseEntity.ok().body("Order Created");
    }
}
