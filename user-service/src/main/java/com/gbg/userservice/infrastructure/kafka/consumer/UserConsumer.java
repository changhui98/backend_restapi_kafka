package com.gbg.userservice.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.userservice.domain.repository.UserRepository;
import com.gbg.userservice.infrastructure.kafka.producer.UserProducer;
import com.gbg.userservice.presentation.dto.request.ValidatorRequest;
import com.gbg.userservice.presentation.dto.response.UserValidatorEvent;
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

    @KafkaListener(topics = "user.validate.request")
    public void validateUser(String message) {
        log.info("3. 사가 오케스트레이션 -> 유저 서비스 메시지 받기 성공");

        UUID orderId;
        UUID userId;

        try {
            UserValidatorEvent event = objectMapper.readValue(message, UserValidatorEvent.class);
            orderId = event.orderId();
            userId = event.userId();

            userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
            );

            log.info("4-1. 유저 서비스 검증 성공 메시지 발행 ");
            userProducer.validatorRequest("user.validate.response", new ValidatorRequest(orderId, userId,"SUCCESS", "사용자 정보 조회 성공"));

        } catch (JsonProcessingException e) {

            log.error("User Json processing error", e);
        }

    }


}
