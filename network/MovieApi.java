package com.practice.sowmya.movie_monkey.network;


import com.practice.sowmya.movie_monkey.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // Get movies list
    @GET("/3/search/movie")
    Call<MoviesResponse> getMovieList(@Query("query") String query, @Query("page") int page);

}
