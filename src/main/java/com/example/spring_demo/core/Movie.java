package com.example.spring_demo.core;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private String title;
    private String link;
    private String actor;
    private String director;
    private float userRating;
}