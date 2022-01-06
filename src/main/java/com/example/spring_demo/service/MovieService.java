package com.example.spring_demo.service;

import com.example.spring_demo.core.Movie;
import com.example.spring_demo.core.MovieGroup;
import com.example.spring_demo.core.MovieRepository;
import com.example.spring_demo.exception.ClientNoContentRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    public Map movieCache = new HashMap<String, List<Movie>>();

    public List<Movie> search(final String query){
        if (!this.movieCache.containsKey(query)){
            MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery(query));
            this.movieCache.put(query, movieGroup.getListOrderRating());
        }
        return (List<Movie>) this.movieCache.get(query);
    }

    public Movie recommendTodayMovie(final String query){
        MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery(query));
        return movieGroup.getHighestRatingMovie() //검색 결과가 없을때는 기본 영화 제공
                .orElseThrow(ClientNoContentRuntimeException::new);
    }

    public Map<String, List<Movie>> lookup(){
        return movieCache;
    }

    public Set update(){
        for(Object query : movieCache.keySet()){
            MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery((String) query));
            movieCache.put(query, movieGroup.getListOrderRating());
        }
        return movieCache.keySet();

    }
}