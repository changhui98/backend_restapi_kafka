package com.gbg.orderservice.presentiation.controller;

import com.gbg.orderservice.application.service.OrderService;
import com.gbg.orderservice.presentiation.dto.request.OrderCreateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
