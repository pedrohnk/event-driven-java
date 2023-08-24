package com.javaguides.orderservice.messaging;

import com.javaguides.orderservice.dto.OrderEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    private Logger LOGGER = LoggerFactory.getLogger(OrderEventPublisher.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEventDto orderEvent){
        LOGGER.info(String.format("Order event sent to RabbitMQ: %s", orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
    }
}
