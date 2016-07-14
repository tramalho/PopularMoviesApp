package br.com.trama.popularmoviesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.trama.popularmoviesapp.R;
import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.model.MovieResponse;
import br.com.trama.popularmoviesapp.network.MovieAsyncTask;
import br.com.trama.popularmoviesapp.ui.adapter.MovieAdapter;
import br.com.trama.popularmoviesapp.util.Const;


public class MainActivity extends AppCompatActivity implements MovieAsyncTask.MovieAsyncTaskCallback, MovieAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MovieModel> moviesList;
    private MovieAdapter movieAdapter;
    private int page = 0;
    private int totalPages = 1;
    private boolean loading = true;
    private ProgressBar progressBar;
    private RecyclerView moviesRV;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildList();
    }

    private void buildList() {

        progressBar = (ProgressBar) findViewById(R.id.movies_progress_id);

        moviesRV = (RecyclerView) findViewById(R.id.movies_recycler_id);

        final GridLayoutManager gridLayoutManager =
                new NpaGridLayoutManager(this, Const.Util.SPAN_COLUMNS);

        this.moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this.moviesList);
        movieAdapter.setOnItemClickListener(this);

        if (moviesRV != null) {
            moviesRV.setLayoutManager(gridLayoutManager);
            moviesRV.setAdapter(movieAdapter);
            moviesRV.setHasFixedSize(true);
            moviesRV.addOnScrollListener(new MyOnScrollListener(gridLayoutManager));
        }

        requestMovies(getString(R.string.most_popular));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getSupportActionBar().getThemedContext(),
                R.array.sort_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    private void requestMovies(String sort) {
        ++page;
        if(page <= this.totalPages) {
            new MovieAsyncTask(this, page, sort).execute();
        }
    }

    @Override
    public void onResult(MovieResponse movieResponse) {
        this.page = movieResponse.getPage();
        this.totalPages = movieResponse.getTotalPages();
        this.moviesList.addAll(movieResponse.getMovieModels());
        this.movieAdapter.notifyDataSetChanged();
        this.loading = false;
        configResetView(false);
    }

    @Override
    public boolean isPopularSort(String sort) {
        return getString(R.string.most_popular).equals(sort);
    }

    private void configResetView(boolean isReset) {
        moviesRV.setVisibility(isReset ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isReset ? View.VISIBLE : View.GONE);

        if(isReset){
            this.page = 0;
            this.totalPages = 1;
            moviesList.clear();
            movieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(MovieModel movieModel) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.Param.MOVIE_MODEL, movieModel);
        this.startActivity(intent);
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            configResetView(true);
            requestMovies(parent.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {

        private final GridLayoutManager gridLayoutManager;
        private int previousTotal;
        private int visibleThreshold;
        int firstVisibleItem, visibleItemCount, totalItemCount;

        public MyOnScrollListener(GridLayoutManager gridLayoutManager) {
            this.gridLayoutManager = gridLayoutManager;
            previousTotal = 0;
            visibleThreshold = 10;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = gridLayoutManager.getItemCount();
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                requestMovies(spinner.getSelectedItem().toString());
                loading = true;
            }
        }
    }

    /**
     * No Predictive Animations GridLayoutManager
     */
    private static class NpaGridLayoutManager extends GridLayoutManager {
        /**
         * Disable predictive animations. There is a bug in RecyclerView which causes views that
         * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
         * adapter size has decreased since the ViewHolder was recycled.
         */
        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public NpaGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public NpaGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public NpaGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
    }
}
