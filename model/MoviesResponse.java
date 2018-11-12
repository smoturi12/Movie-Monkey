package com.practice.sowmya.movie_monkey.model;

import com.squareup.moshi.Json;

import java.util.List;

public class MoviesResponse {

    @Json(name = "results")
    private List<Movie> movies;

    @Json(name = "total_pages")
    private int totalPages;

    public List<Movie> getMovieList() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movies = movieList;
    }
}
