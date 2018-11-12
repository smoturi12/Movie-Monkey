package com.practice.sowmya.movie_monkey.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.practice.sowmya.movie_monkey.R;
import com.practice.sowmya.movie_monkey.model.Movie;
import com.practice.sowmya.movie_monkey.network.ApiWarden;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    private SearchMVPContract.View view;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_title)
        TextView titleView;
        @BindView(R.id.movie_image)
        ImageView imageView;

        public Movie movie;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }

        @Override
        public void onClick(View view) {
            SearchAdapter.this.view.onMovieClicked(movie);
        }
    }

    public SearchAdapter(List<Movie> movies, SearchMVPContract.View moviesView) {
        this.movies = movies;
        view = moviesView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.movie_grid_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(holder);
        holder.movie = movies.get(position);
        holder.titleView.setText(holder.movie.getTitle());

        String imageUrl = holder.movie.getPosterPath();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .priority(Priority.HIGH);


            Glide.with(context)
                    .asBitmap()
                    .load(ApiWarden.getImagePath(holder.movie.getPosterPath()))
                    .apply(options)
                    .into(new BitmapImageViewTarget(holder.imageView) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(bitmap, transition);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
