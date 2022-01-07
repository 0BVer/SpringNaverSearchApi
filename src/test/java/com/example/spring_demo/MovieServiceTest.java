package com.example.spring_demo;

import com.example.spring_demo.core.Movie;
import com.example.spring_demo.core.MovieRepository;
import com.example.spring_demo.exception.ClientNoContentRuntimeException;
import com.example.spring_demo.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

//    @DisplayName("평점 순으로 정렬되는지")
//    @Test
//    void shouldSortedInOrderOfGrade(){
//        //given
//        String query = "test_query";
//        String expectedTopRankingMovieTile = "movie3";
//        MovieRepository movieRepository = new MockMovieRepositoryImpl(null, null);
//        MovieService movieService = new MovieService(movieRepository);
//
//        //when
//        List<Movie> actualMovie = movieService.search(query);
//
//        //then
//        assertEquals(expectedTopRankingMovieTile, actualMovie.stream().findFirst().get().getTitle());
//    }

    @Mock
    private MovieRepository movieRepository;

    @DisplayName("평점이 0인 영화를 제외하는지")
    @Test
    void shouldExcludeGradeIsZero(){
        //given
        String query = "test_query";
        int expectedMovieSize = 2;
        List<Movie> getStubMovies = Arrays.asList(
                Movie.builder().title("movie1").link("http://test").director("a").userRating(0).build(),
                Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
                Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build()
        );
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies);
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
        List<Movie> getStubMovies = Arrays.asList(
                Movie.builder().title("movie1").link("http://test").director("").userRating(7.9f).build(),
                Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
                Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build()
        );
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies);
        MovieService movieService = new MovieService(movieRepository);

        //when
        List<Movie> actualMovie = movieService.search(query);

        //then
        assertEquals(expectedMovieSize, actualMovie.size());
    }

    @DisplayName("추천 영화로 펼점이 제일 높은 영화를 제공하는지")
    @Test
    void shouldRecommendHighestRating(){
        //given
        String expectedRecommendMovie = "movie3";
        List<Movie> getStubMovies = Arrays.asList(
                    Movie.builder().title("movie1").link("http://test").director("a").userRating(7.9f).build(),
                    Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
                    Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build()
            );
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies);
        MovieService movieService = new MovieService(movieRepository);

        //when
        Movie actualMovie = movieService.recommendTodayMovie("test_case");

        //then
        assertEquals(expectedRecommendMovie, actualMovie.getTitle());
    }

//    @DisplayName("추천 영화의 검색 결과가 없을때 디폴트 영화를 제공하는지")
//    @Test
//    void shouldReturnNullWhenRecommendNoResult(){
//        //given
//        String expectedReturnTitle = "basic movie";
//        given(movieRepository.findByQuery(anyString())).willReturn(Collections.emptyList());
//        MovieService movieService = new MovieService(movieRepository);
//
//        //when
//        Movie actualReturn = movieService.recommendTodayMovie("test_case");
//
//        //then
//        assertEquals(expectedReturnTitle, actualReturn.getTitle());
//    }

    @DisplayName("추천 영화의 검색 결과가 없을때 예외를 발생하는지")
    @Test
    void shouldThrowExceptionWhenNoneRecommed(){
        //given
        given(movieRepository.findByQuery(anyString())).willReturn(Collections.emptyList());
        MovieService movieService = new MovieService(movieRepository);

        //when, then
        assertThrows(ClientNoContentRuntimeException.class, () -> {
            Movie movie = movieService.recommendTodayMovie(anyString());
        });
    }

//    @DisplayName("캐시에 값이 잘 저장 되는지")
//    @Test
//    void shouldSaveResultToCache(){
//        //given
//        String query = "test_query";
//        List<Movie> getStubMovies = Arrays.asList(
//                Movie.builder().title("movie1").link("http://test").director("a").userRating(7.9f).build(),
//                Movie.builder().title("movie2").link("http://test").director("a").userRating(8.3f).build(),
//                Movie.builder().title("movie3").link("http://test").director("a").userRating(9.7f).build()
//        );
//        int expectedCacheSize = 1;
//        given(movieRepository.findByQuery(anyString())).willReturn(getStubMovies);
//        MovieService movieService = new MovieService(movieRepository);
//
//        //when
//        movieService.search(query);
//
//        //then
//        assertEquals(expectedCacheSize, movieService.lookup().size());
//    }

    @DisplayName("값을 잘 갱신하는지")
    @Test
    void shouldRecallCacheWell(){
        //given
        //TODO:값 갱신 작동 하는지 테스트 코드 작성
    }
}

@ActiveProfiles("secret")
//@SpringBootTest(classes = MovieService.class) //단위 테스트?
@SpringBootTest //통합 테스트
class MovieServiceSpringBootTest{

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @DisplayName("기본 검색에서 평점 순으로 정렬되는지 검증")
    @Test
    void shouldSortedInOrderOfGrade(){
        //given
        String query = "test_query";
        String expectedTopRankingMovieTitle = "평점1위";
        List<Movie> getStubMocies = Arrays.asList(
                Movie.builder().title("평점없음").link("http://test").director("a").userRating(0.0f).build(),
                Movie.builder().title("감독없음").link("http://test").director("").userRating(8.3f).build(),
                Movie.builder().title("평점2위").link("http://test").director("a").userRating(8.3f).build(),
                Movie.builder().title("평점1위").link("http://test").director("a").userRating(9.7f).build()
        );
        given(movieRepository.findByQuery(anyString())).willReturn(getStubMocies);

        //when
        List<Movie> actualMovies = movieService.search(query);

        //then
        assertEquals(expectedTopRankingMovieTitle, actualMovies.stream().findFirst().get().getTitle());
    }
}