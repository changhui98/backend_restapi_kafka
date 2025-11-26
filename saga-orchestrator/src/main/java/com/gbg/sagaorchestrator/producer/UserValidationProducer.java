package com.gbg.sagaorchestrator.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.dto.UserValidatorRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendUserValidation(UserValidatorRequestEvent event) {
        try {

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user.validate.request", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 직렬화 실패" + e.getMessage());
        }
    }


}
