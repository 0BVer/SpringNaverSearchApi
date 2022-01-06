package com.example.spring_demo.core;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class Movie implements Serializable {
    private String title;
    private String link;
    private String actor;
    private String director;
    private float userRating;
}