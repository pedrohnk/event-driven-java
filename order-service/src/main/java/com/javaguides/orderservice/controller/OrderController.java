package com.javaguides.orderservice.controller;

import com.javaguides.orderservice.dto.OrderDto;
import com.javaguides.orderservice.dto.OrderEventDto;
import com.javaguides.orderservice.messaging.OrderEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private OrderEventPublisher producer;

    public OrderController(OrderEventPublisher producer) {
        this.producer = producer;
    }

    @PostMapping("/orders")
    public ResponseEntity<HttpResponse> placeOrder(@RequestBody OrderDto order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEventDto event = new OrderEventDto();
        event.setStatus("PENDING");
        event.setMessage("Order is in pending status");
        event.setOrder(order);
        producer.sendMessage(event);
        HttpResponse response = new HttpResponse(event);
        return ResponseEntity.ok(response);
    }
}
