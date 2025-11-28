package com.gbg.userservice.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.userservice.presentation.dto.request.ValidatorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper =  new ObjectMapper();
    String message;

    public void validatorRequest(String topic, ValidatorRequest req) {

        try {
            message = objectMapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            log.error("Error serializing validator request", e);
        }

        kafkaTemplate.send(topic,
            req.orderId().toString(),
            message);
    }


}
