package com.gbg.userservice.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.userservice.infrastructure.kafka.event.command.UserValidateEventBase;
import com.gbg.userservice.infrastructure.kafka.event.command.UserValidateSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void validatorRequest(String topic, UserValidateEventBase event) {

        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, message);
            log.info("send success -> saga service {}", message);
        } catch (JsonProcessingException e) {

            log.error("send failed -> saga service {}", e.getMessage());
        }
    }


}
