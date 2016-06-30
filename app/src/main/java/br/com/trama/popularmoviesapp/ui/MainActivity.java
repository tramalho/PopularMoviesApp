package br.com.trama.popularmoviesapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.trama.popularmoviesapp.R;
import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.model.MovieResponse;
import br.com.trama.popularmoviesapp.network.MovieAsyncTask;
import br.com.trama.popularmoviesapp.ui.adapter.MovieAdapter;



public class MainActivity extends AppCompatActivity implements MovieAsyncTask.MovieAsyncTaskCallback {

    private List<MovieModel> moviesList;
    private MovieAdapter movieAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildList();
    }

    private void buildList() {
        RecyclerView moviesRV = (RecyclerView) findViewById(R.id.movies_recycler_id);

        GridLayoutManager staggeredGridLayoutManager =
                new GridLayoutManager(this, 3);

        moviesRV.setLayoutManager(staggeredGridLayoutManager);

        this.moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this.moviesList);
        moviesRV.setAdapter(movieAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestMovies(this.page);
    }

    private void requestMovies(int page) {
        new MovieAsyncTask(this, page).execute();
    }

    @Override
    public void onResult(MovieResponse movieResponse) {
        this.page = movieResponse.getPage();
        this.moviesList.addAll(movieResponse.getMovieModels());
        this.movieAdapter.notifyDataSetChanged();
    }
}
