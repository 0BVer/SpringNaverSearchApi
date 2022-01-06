package com.example.spring_demo;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class MockMovieRepositoryImpl extends MovieRepositoryImpl{
    public MockMovieRepositoryImpl(RestTemplate restTemplate, NaverProperties naverProperties) {
        super(restTemplate, naverProperties);
    }
    @Override
    public List<Movie> findByQuery(final String query){
        return Arrays.asList(
                Movie.builder().title("movie1").link("http://test").director("a").userRating(9.3f).build(),
                Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
                Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build(),
                Movie.builder().title("movie4").link("http://test").director("").userRating(9.8f).build()
        );
    }
}
