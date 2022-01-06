package com.example.spring_demo.web;


import com.example.spring_demo.core.Encyc;
import com.example.spring_demo.service.EncycService;
import com.example.spring_demo.core.Movie;
import com.example.spring_demo.service.MovieService;
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
    public List<Movie> getMoviesByQuery(@RequestParam(name = "q") String query) {
        return movieService.search(query);
    }

    @GetMapping("/recommend")
    public Movie getMovieByQuery(@RequestParam(name = "q") String query) {
        return movieService.recommendTodayMovie(query);
    }

    @GetMapping("/encyc")
    public List<Encyc> getEncycByQuery(@RequestParam(name = "q") String query) {
        return encycService.search(query);
    }
}