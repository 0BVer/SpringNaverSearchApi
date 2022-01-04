package com.example.spring_demo;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class Encyc implements Serializable {
    private String title;
    private String link;
    private String description;
}
