package com.example.spring_demo;

import java.util.List;

public interface EncycRepository {
    List<Encyc> findByQuery(String query);
}
