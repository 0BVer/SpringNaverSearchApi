package com.example.spring_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "인증 되지 않음")
public class ClientAuthRuntimeException extends RuntimeException{
    public ClientAuthRuntimeException(ExceptionMessage message){
        super(message.getMessage());
    }

    public ClientAuthRuntimeException(String message){
        super(message);
    }
}
