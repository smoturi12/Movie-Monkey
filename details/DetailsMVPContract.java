package com.practice.sowmya.movie_monkey.details;

import com.practice.sowmya.movie_monkey.framework.BaseContract;
import com.practice.sowmya.movie_monkey.model.Movie;

public interface DetailsMVPContract {

    interface View extends BaseContract.View {
        void updateMovieDetails(Movie movie);
    }

    interface Presenter extends BaseContract.Presenter {
        void showDetails(Movie movie);
    }
}
