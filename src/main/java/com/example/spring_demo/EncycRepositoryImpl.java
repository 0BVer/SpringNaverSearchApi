package com.example.spring_demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EncycRepositoryImpl implements EncycRepository {

    private final RestTemplate restTemplate;
    private final NaverProperties naverProperties;

    public EncycRepositoryImpl(RestTemplate restTemplate, NaverProperties naverProperties) {
        this.restTemplate = restTemplate;
        this.naverProperties = naverProperties;
    }

    @Override
    public List<Encyc> findByQuery(String query) {
        org.springframework.http.HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Naver-Client-Id", naverProperties.getClientId());
        httpHeaders.add("X-Naver-Client-Secret", naverProperties.getClientSecret());

        String url = naverProperties.getUrl() + "encyc.json?query=" + query;
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(httpHeaders), ResponseEncyc.class)
                .getBody()
                .getItems()
                .stream()
                .map(m -> Encyc.builder()
                        .title(m.getTitle())
                        .link(m.getLink())
                        .description(m.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
