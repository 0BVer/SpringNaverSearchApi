package com.example.spring_demo.core;

import com.example.spring_demo.provider.timer.PerformanceTimeRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieGroup {
    private final List<Movie> list;

    public MovieGroup (final  List<Movie> lsit){
        this.list = lsit;
    }

    public List<Movie> getListOrderRating(){
        return list.stream()
                .filter(b -> !((Float)b.getUserRating()).equals(0.0f))
                .filter(b -> !b.getDirector().equals(""))
                .sorted((a, b) -> b.getUserRating() >= a.getUserRating() ? 1 : -1)
                .collect(Collectors.toList());
    }

    public Optional<Movie> getHighestRatingMovie(){
        return getListOrderRating().stream().findFirst();
    }

    @PerformanceTimeRecord
    public List<Movie> findAllByActor(final String actor){
        try{
            Thread.sleep(3000);

            return Arrays.asList(Movie.builder().title("Movie1").build());
        } catch (InterruptedException e) {
            throw new RuntimeException("test");
        }
    }
}
