package com.example.spring_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "검색 결과 없음")
public class ClientNoContentRuntimeException extends RuntimeException{
    public ClientNoContentRuntimeException(){
        super(ExceptionMessage.NO_CONTENT.getMessage());
    }
}
