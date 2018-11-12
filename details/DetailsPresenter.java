package com.practice.sowmya.movie_monkey.details;

import com.practice.sowmya.movie_monkey.model.Movie;

public class DetailsPresenter implements DetailsMVPContract.Presenter{

    private DetailsMVPContract.View view;


    public DetailsPresenter(DetailsMVPContract.View view) {
        this.view = view;
    }

    @Override
    public void showDetails(Movie movie) {
        view.updateMovieDetails(movie);
    }

    @Override
    public void dispose() {

    }
}
