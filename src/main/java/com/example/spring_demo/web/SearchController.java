package com.example.spring_demo.web;


import com.example.spring_demo.core.Encyc;
import com.example.spring_demo.service.EncycService;
import com.example.spring_demo.core.Movie;
import com.example.spring_demo.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @GetMapping("/movies/cache")
    public Map<String, List<Movie>> getMovieCacheUpdate(){
        return movieService.lookup();
    }

    @PutMapping("/movies/cache")
    public Set putMovieCacheUpdate(){
        return movieService.update();
    }

    @GetMapping("/encyc")
    public List<Encyc> getEncycByQuery(@RequestParam(name = "q") String query) {
        return encycService.search(query);
    }
}