package com.practice.sowmya.movie_monkey.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.practice.sowmya.movie_monkey.Constants;
import com.practice.sowmya.movie_monkey.R;
import com.practice.sowmya.movie_monkey.framework.BaseActivity;
import com.practice.sowmya.movie_monkey.model.Movie;
import com.practice.sowmya.movie_monkey.network.ApiWarden;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends BaseActivity implements DetailsMVPContract.View{
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private Unbinder unbinder;
    @NonNull
    private DetailsPresenter presenter;

    @BindView(R.id.movie_poster)
    ImageView imageView;

    @BindView(R.id.movie_title)
    TextView titleView;

    @BindView(R.id.movie_release_date)
    TextView releaseDateView;

    @BindView(R.id.movie_overview)
    TextView overviewView;

    @BindView(R.id.error_view)
    TextView errorView;

    @Override
    public void updateMovieDetails(Movie movie) {
        Log.d(TAG, "updateMovieDetails():" + movie);
        if (movie != null) {
            String imageUrl = movie.getBackdropPath();
            if (imageUrl != null && imageUrl.isEmpty()) {
                RequestOptions options = new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .priority(Priority.HIGH)
                        .error(R.drawable.placeholder);


                Glide.with(this)
                        .asBitmap()
                        .load(ApiWarden.getImagePath(movie.getBackdropPath()))
                        .apply(options)
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(bitmap, transition);
                            }
                        });
            }
            titleView.setText(movie.getTitle());
            releaseDateView.setText(movie.getReleaseDate());
            overviewView.setText(movie.getOverview());
        } else {
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        unbinder = ButterKnife.bind(this);
        presenter = new DetailsPresenter(this);
        setToolbar(R.string.details_page_title);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(Constants.MOVIE))
            {
                Movie movie = extras.getParcelable(Constants.MOVIE);
                if (movie != null)
                {
                    presenter.showDetails(movie);
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
        unbinder.unbind();
    }

}
