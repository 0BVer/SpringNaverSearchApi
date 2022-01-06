package com.example.spring_demo.client;

import com.example.spring_demo.core.Movie;
import com.example.spring_demo.core.MovieRepository;
import com.example.spring_demo.config.NaverProperties;
import com.example.spring_demo.exception.ClientAuthRuntimeException;
import com.example.spring_demo.exception.ClientBadRequestRuntimeException;
import com.example.spring_demo.exception.ClientRuntimeException;
import com.example.spring_demo.exception.ExceptionMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieRepositoryImpl implements MovieRepository {

    private final RestTemplate restTemplate;
    private final NaverProperties naverProperties;
    private HttpHeaders httpHeaders = new HttpHeaders();

    public MovieRepositoryImpl(RestTemplate restTemplate, NaverProperties naverProperties) {
        this.restTemplate = restTemplate;
        this.naverProperties = naverProperties;

        this.httpHeaders.add("X-Naver-Client-Id", naverProperties.getClientId());
        this.httpHeaders.add("X-Naver-Client-Secret", naverProperties.getClientSecret());
    }

    @Override
    public List<Movie> findByQuery(String query) {

        String url = naverProperties.getUrl() + "movie.json?query=" + query;

        try {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(httpHeaders), ResponseMovie.class)
                    .getBody()
                    .getItems()
                    .stream()
                    .map(m -> Movie.builder()
                            .title(m.getTitle())
                            .link(m.getLink())
                            .actor(m.getActor())
                            .director(m.getDirector())
                            .userRating(m.getUserRating())
                            .build())
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException ex) {
            if (HttpStatus.UNAUTHORIZED.equals(ex.getStatusCode())) {
                throw new ClientAuthRuntimeException(ExceptionMessage.NAVER_API_UNAUTORIZED);
            } else if (HttpStatus.BAD_REQUEST.equals(ex.getStatusCode())) {
                throw new ClientBadRequestRuntimeException(ExceptionMessage.NAVER_API_BAD_REQUEST);
            } else {
                throw new ClientRuntimeException(ex.getMessage());
            }
        } catch (RuntimeException ex) {
            throw new ClientRuntimeException(ex.getMessage());
        }
    }
}
