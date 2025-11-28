package com.gbg.userservice.application.service;

import com.gbg.userservice.domain.entity.User;
import com.gbg.userservice.domain.repository.UserRepository;
import com.gbg.userservice.infrastructure.client.OrderClient;
import com.gbg.userservice.infrastructure.kafka.event.command.UserValidateFailEvent;
import com.gbg.userservice.infrastructure.kafka.event.command.UserValidateSuccessEvent;
import com.gbg.userservice.infrastructure.kafka.event.request.UserValidateEvent;
import com.gbg.userservice.infrastructure.kafka.producer.UserProducer;
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
    private final UserProducer userProducer;

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

    public void validateUser(UserValidateEvent event) {

        User user = userRepository.findById(event.userId()).orElse(null);
        if (user == null) {
            userProducer.validatorRequest("user-validate-fail"
                , new UserValidateFailEvent(
                    event.orderId(),
                    event.userId(),
                    "user-not-found"
                ));
        }

        userProducer.validatorRequest("user-validate-success",
            new UserValidateSuccessEvent(
                event.orderId(),
                event.userId()
            ));
    }

    private List<OrderResponse> getOrder(UUID userId) {
        List<OrderResponse> orders = orderClient.getOrders(userId);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found");
        }
        return orders;
    }
}
