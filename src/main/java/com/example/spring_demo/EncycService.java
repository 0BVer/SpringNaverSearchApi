package com.example.spring_demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncycService {

    private final EncycRepository encycRepository;

    public EncycService(EncycRepository encycRepository) {
        this.encycRepository = encycRepository;
    }

    public List<Encyc> search(final String query){
        return encycRepository.findByQuery(query);
    }
}
