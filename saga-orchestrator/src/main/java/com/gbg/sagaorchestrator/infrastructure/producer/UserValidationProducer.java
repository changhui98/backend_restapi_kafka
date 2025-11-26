package com.gbg.sagaorchestrator.infrastructure.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.presentation.dto.UserValidatorRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendUserValidation(UserValidatorRequestEvent event) {
        try {

            String message = objectMapper.writeValueAsString(event);
            log.info("2. 유저 서비스로 유저 검증 메시지 보내기");
            kafkaTemplate.send("user.validate.request", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 직렬화 실패" + e.getMessage());
        }
    }


}
