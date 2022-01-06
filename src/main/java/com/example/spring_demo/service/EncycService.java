package com.example.spring_demo.service;

import com.example.spring_demo.core.Encyc;
import com.example.spring_demo.core.EncycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncycService {

    private final EncycRepository encycRepository;

    public List<Encyc> search(final String query){
        return encycRepository.findByQuery(query);
    }
}
