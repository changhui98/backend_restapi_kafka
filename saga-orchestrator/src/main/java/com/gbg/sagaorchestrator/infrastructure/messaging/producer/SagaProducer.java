package com.gbg.sagaorchestrator.infrastructure.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.command.UserValidateCommand;
import com.gbg.sagaorchestrator.infrastructure.messaging.event.outcome.OrderCreateFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendUserValidate(UserValidateCommand event) {
        try {

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user.validate.request",
                event.orderId().toString(),
                message);
            log.info("send success -> user validate {}", message);

        } catch (JsonProcessingException e) {

            log.error("send failed -> user validate {}", e.getMessage());
        }
    }

    public void sendOrderFailed(OrderCreateFailedEvent event) {

        try {

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order.create.failed", message);
            log.info("send success ->  order failed {}", message);

        } catch (JsonProcessingException e) {

            log.error("send failed -> order failed {}" , e.getMessage());
        }
    }


}
