package com.javaguides.orderservice.controller;

import lombok.Data;

@Data
public class HttpResponse {
    private Object data;

    public HttpResponse(Object data) {
        this.data = data;
    }
}
