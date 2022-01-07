package com.example.spring_demo.service;

import com.example.spring_demo.core.Movie;
import com.example.spring_demo.core.MovieGroup;
import com.example.spring_demo.core.MovieRepository;
import com.example.spring_demo.exception.ClientNoContentRuntimeException;
import com.example.spring_demo.provider.cache.LookAsideCaching;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

//    @PerformanceTimeRecord
    @LookAsideCaching(value = "cache::search-movies", key = "query")
    public List<Movie> search(final String query) {
        MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery(query));
        return movieGroup.getListOrderRating();
    }

//    @PerformanceTimeRecord
    @LookAsideCaching(value = "cache::recommend-movies", key = "query")
    public Movie recommendTodayMovie(final String query) {
        MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery(query));
        return movieGroup.getHighestRatingMovie() //검색 결과가 없을때는 기본 영화 제공
                .orElseThrow(ClientNoContentRuntimeException::new);
    }

    public List<Movie> findActor(final String query) {
        MovieGroup movieGroup = new MovieGroup(movieRepository.findByQuery(query));
        return movieGroup.findAllByActor(query);
    }
}