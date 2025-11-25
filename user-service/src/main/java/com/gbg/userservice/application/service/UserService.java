package com.gbg.userservice.application.service;

import com.gbg.userservice.domain.entity.User;
import com.gbg.userservice.domain.repository.UserRepository;
import com.gbg.userservice.infrastructure.client.OrderClient;
import com.gbg.userservice.presentation.dto.request.UserSignRequest;
import com.gbg.userservice.presentation.dto.response.OrderResponse;
import com.gbg.userservice.presentation.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderClient orderClient;

    public UUID createUser(UserSignRequest userSignRequest) {

        User user = User.of(userSignRequest.username(), userSignRequest.password());

        return userRepository.create(user);
    }

    public UserResponse getUser(UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        UserResponse userResponse = UserResponse.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .build();

        userResponse.setOrders(getOrder(userId));

        return userResponse;
    }

    private List<OrderResponse> getOrder(UUID userId) {
        List<OrderResponse> orders = orderClient.getOrders(userId);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found");
        }
        return orders;
    }
}
