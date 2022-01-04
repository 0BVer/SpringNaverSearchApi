package com.example.spring_demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")

public class SearchController {

    @Autowired
    private MovieService ms;
    @Autowired
    private EncycService es;

    @GetMapping("/movies")
    public List<Movie> getMoviesByQuery(@RequestParam(name = "q") String query){
        return ms.search(query);
    }

    @GetMapping("/encyc")
    public List<Encyc> getEncycByQuery(@RequestParam(name = "q") String query){
        return es.search(query);
    }
}