package com.example.spring_demo.core;

import java.util.List;

public interface EncycRepository {
    List<Encyc> findByQuery(String query);
}
