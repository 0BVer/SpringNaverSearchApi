package com.example.spring_demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @DisplayName("평점 순으로 정렬되는지")
    @Test
    void shouldSortedInOrderOfGrade(){
        //given
        String query = "test_query";
        String expectedTopRankingMovieTile = "movie3";
        MovieRepository movieRepository = new MockMovieRepositoryImpl(null, null);
        MovieService movieService = new MovieService(movieRepository);

        //when
        List<Movie> actualMovie = movieService.search(query);

        //then
        assertEquals(expectedTopRankingMovieTile, actualMovie.stream().findFirst().get().getTitle());
    }

    @Mock
    private MovieRepository movieRepository;

    @DisplayName("평점이 0인 영화를 제외하는지")
    @Test
    void shouldExcludeGradeIsZero(){
        //given
        String query = "test_query";
        int expectedMovieSize = 2;
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies());
        MovieService movieService = new MovieService(movieRepository);

        //when
        List<Movie> actualMovie = movieService.search(query);

        //then
        assertEquals(expectedMovieSize, actualMovie.size());
    }

    @DisplayName("영화 감독이 없는 영화를 제외하는지")
    @Test
    void shouldExcludeNoDiretor(){
        //given
        String query = "test_query";
        int expectedMovieSize = 2;
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies());
        MovieService movieService = new MovieService(movieRepository);

        //when
        List<Movie> actualMovie = movieService.search(query);

        //then
        assertEquals(expectedMovieSize, actualMovie.size());
    }

    List<Movie> getStubMovies() {
        return Arrays.asList(
                Movie.builder().title("movie1").link("http://test").director("a").userRating(0).build(),
                Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
                Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build()
        );
    }
}