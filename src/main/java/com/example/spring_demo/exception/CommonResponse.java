package com.example.spring_demo.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse {
    private String message;
    private int status;
}
