package com.gbg.userservice.infrastructure.client;

import com.gbg.userservice.presentation.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/order/{userId}")
    List<OrderResponse> getOrders(@PathVariable UUID userId);

}
