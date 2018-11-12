package com.practice.sowmya.movie_monkey.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.practice.sowmya.movie_monkey.Constants;
import com.practice.sowmya.movie_monkey.R;
import com.practice.sowmya.movie_monkey.details.DetailsActivity;
import com.practice.sowmya.movie_monkey.framework.BaseActivity;
import com.practice.sowmya.movie_monkey.model.Movie;
import com.practice.sowmya.movie_monkey.network.ApiWarden;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchActivity extends BaseActivity implements SearchMVPContract.View {
    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final int COLUMNS = 3;

    @BindView(R.id.movie_results)
    RecyclerView moviesResultsView;

    @BindView(R.id.error_textView)
    TextView errorTextView;

    @NonNull
    private SearchPresenter presenter;
    @NonNull
    private SearchAdapter adapter;
    private Unbinder unbinder;
    private List<Movie> movies = new ArrayList();
    private String currentQuery = Constants.DEFAULT_QUERY;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        ApiWarden.init().setup();
        initLayoutReferences();
        setToolbar(R.string.search_page_title);
        presenter = new SearchPresenter(this);

        moviesResultsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (currentPage < presenter.getTotalPages() && !recyclerView.canScrollVertically(1)) {
                    currentPage++;
                    presenter.fetchSearchResults(currentQuery,currentPage);
                }
            }
        });

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelable(Constants.MOVIE);
            adapter.notifyDataSetChanged();
        } else {
            presenter.fetchSearchResults(currentQuery,1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                resetResultsState();
                Log.d(TAG, "onQueryTextSubmit: " + currentQuery);
                if (!query.isEmpty() && searchView.isEnabled()) {
                    presenter.fetchSearchResults(currentQuery,currentPage);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                System.out.println("onMenuItemActionExpand()" + item);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                System.out.println("onMenuItemActionCollapse()");
                return true;
            }
        });

        return true;
    }

    private void initLayoutReferences() {
        moviesResultsView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, COLUMNS);

        moviesResultsView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(movies, this);
        moviesResultsView.setAdapter(adapter);
    }

    private void resetResultsState() {
        currentPage = 1;
        movies.clear();
        moviesResultsView.removeAllViews();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.MOVIE, movie);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void updateSearchResults(List<Movie> movieList) {
//        moviesResultsView.removeAllViews();
        movies.addAll(movieList);

        if (movies.isEmpty()) {
            errorTextView.setText(R.string.no_results_text);
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            adapter.notifyDataSetChanged();
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
        //TODO show some kind of error view.
    }

    @Override
    public void showLoading(boolean show) {
        //TODO show progressbar in spinner style
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
        unbinder.unbind();
    }
}
