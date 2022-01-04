package com.example.spring_demo;

import java.util.List;

public interface MovieRepository {
    List<Movie> findByQuery(String query);
}
