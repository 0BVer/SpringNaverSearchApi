package com.example.spring_demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final MovieService movieService;
    private final EncycService encycService;

    public SearchController(MovieService movieService, EncycService encycService) {
        this.movieService = movieService;
        this.encycService = encycService;
    }

    @GetMapping("/movies")
    public List<Movie> getMoviesByQuery(@RequestParam(name = "q") String query){
        return movieService.search(query);
    }

    @GetMapping("/encyc")
    public List<Encyc> getEncycByQuery(@RequestParam(name = "q") String query){
        return encycService.search(query);
    }
}