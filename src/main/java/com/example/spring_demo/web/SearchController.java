package com.example.spring_demo.web;


import com.example.spring_demo.core.Encyc;
import com.example.spring_demo.provider.cache.CachingAspectProvider;
import com.example.spring_demo.service.EncycService;
import com.example.spring_demo.core.Movie;
import com.example.spring_demo.service.MovieService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final MovieService movieService;
    private final EncycService encycService;
    private final CachingAspectProvider cachingAspectProvider;

    public SearchController(MovieService movieService, EncycService encycService, CachingAspectProvider cachingAspectProvider) {
        this.movieService = movieService;
        this.encycService = encycService;
        this.cachingAspectProvider = cachingAspectProvider;
    }

    @GetMapping("/movies")
    public List<Movie> getMoviesByQuery(@RequestParam(name = "q") String query) {
        return movieService.search(query);
    }

    @GetMapping("/recommend")
    public Movie getMovieByQuery(@RequestParam(name = "q") String query) {
        return movieService.recommendTodayMovie(query);
    }

//    @CacheEvict(value = "cache::movie::query", key = "#query")
//    @DeleteMapping("/movies/cache")
//    public Boolean clearMovieQueryCache(@RequestParam(name = "q") String q){
//        return true;
//    }

    @GetMapping("movies/actors")
    public List<Movie> getActorsByQuery(@RequestParam(name = "q") String query){
        return movieService.findActor(query);
    }

    @GetMapping("/encyc")
    public List<Encyc> getEncycByQuery(@RequestParam(name = "q") String query) {
        return encycService.search(query);
    }
}