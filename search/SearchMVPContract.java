package com.practice.sowmya.movie_monkey.search;

import com.practice.sowmya.movie_monkey.framework.BaseContract;
import com.practice.sowmya.movie_monkey.model.Movie;

import java.util.List;

public interface SearchMVPContract {

    interface View extends BaseContract.View {
        void updateSearchResults(List<Movie> movies);
        void onMovieClicked(Movie movie);
    }

    interface Presenter extends BaseContract.Presenter {
        void fetchSearchResults(String query, int page);
        void setView(View view);
    }
}
