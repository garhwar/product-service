package com.pesto.productservice.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesto.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class KafkaMessageListener {

    @Autowired
    ProductService productService;

    @KafkaListener(topics = "order-topic", groupId = "order-created-consumers")
    public void processOrderMessage(String orderMessage) throws JsonProcessingException {
        List<OrderMessage.OrderProduct> orderProducts = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            orderProducts = objectMapper.readValue(
                    orderMessage, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to deserialize message");
        }
        if (!Objects.isNull(orderProducts))
            productService.processOrder(orderProducts);
    }
}
