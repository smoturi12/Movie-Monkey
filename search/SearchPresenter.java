package com.practice.sowmya.movie_monkey.search;

import android.util.Log;

import com.practice.sowmya.movie_monkey.model.Movie;
import com.practice.sowmya.movie_monkey.model.MoviesResponse;
import com.practice.sowmya.movie_monkey.network.ApiWarden;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements SearchMVPContract.Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchMVPContract.View view;
    private List<Movie> movies = new ArrayList<>();
    private int totalPages;

    public SearchPresenter(SearchMVPContract.View view) {
        this.view = view;
    }

    public void fetchSearchResults(String query, int currentPage) {
        Log.d(TAG,"fetchSearchResults(): Loading page " + currentPage);
        final Call<MoviesResponse> getMoviesCall = ApiWarden.getInstance().api().getMovieList(query, currentPage);

        view.showLoading(true);
        getMoviesCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, "onResponse()");
                totalPages = response.body().getTotalPages();
                view.showLoading(false);
                if (response.isSuccessful()) {
                    List<Movie> movieList = response.body().getMovieList();
                    for (Movie movie : movieList) {
                        Log.d(TAG, "movie: " + movie.toString());
                    }
                    view.updateSearchResults(movieList);
                } else {
                    Log.d(TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure()");
                view.showLoading(false);
                t.printStackTrace();
            }
        });
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setView(SearchMVPContract.View view) {
        view.updateSearchResults(movies);
    }
}
