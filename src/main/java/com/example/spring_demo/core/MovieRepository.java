package com.example.spring_demo.core;

import java.util.List;

public interface MovieRepository {
    List<Movie> findByQuery(String query);
}
