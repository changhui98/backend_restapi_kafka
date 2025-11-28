package com.gbg.userservice.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.userservice.application.service.UserService;
import com.gbg.userservice.domain.repository.UserRepository;
import com.gbg.userservice.infrastructure.kafka.producer.UserProducer;
import com.gbg.userservice.infrastructure.kafka.event.command.UserValidateSuccessEvent;
import com.gbg.userservice.infrastructure.kafka.event.request.UserValidateEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserProducer userProducer;
    private final UserService userService;

    @KafkaListener(topics = "user.validate.request")
    public void validateUser(String message) {
        try {
            log.info("Join User Service message: {}", message);

            UserValidateEvent event = objectMapper.readValue(message, UserValidateEvent.class);
            userService.validateUser(event);

            log.info("User validated successfully");
        } catch (JsonProcessingException e) {

            log.error("User Json processing error", e);
        }
    }
}
