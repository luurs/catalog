package com.lera.catalog.service;

import com.lera.catalog.dto.orders.GoodsInvalidateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, GoodsInvalidateMessage> kafkaTemplate;
    @Value("${spring.kafka.topics.invalidate-goods-cache}")
    private String invalidateGoodsCacheTopic;

    public void sendInvalidateMessage(GoodsInvalidateMessage message) {
        kafkaTemplate.send(invalidateGoodsCacheTopic, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send goods invalidation message: {}", ex.getMessage());
                    } else {
                        log.debug("Goods invalidation sent, offset = {}", result.getRecordMetadata().offset());
                    }
                });
        kafkaTemplate.flush();
    }
}
