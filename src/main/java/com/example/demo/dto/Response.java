package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String message;
    private String code;

    public Response(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
