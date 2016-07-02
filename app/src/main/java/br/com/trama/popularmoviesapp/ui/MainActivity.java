package br.com.trama.popularmoviesapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.trama.popularmoviesapp.R;
import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.model.MovieResponse;
import br.com.trama.popularmoviesapp.network.MovieAsyncTask;
import br.com.trama.popularmoviesapp.ui.adapter.MovieAdapter;
import br.com.trama.popularmoviesapp.util.Const;


public class MainActivity extends AppCompatActivity implements MovieAsyncTask.MovieAsyncTaskCallback, MovieAdapter.OnItemClickListener {

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

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, Const.Util.SPAN_COLUMNS);

        this.moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this.moviesList);
        movieAdapter.setOnItemClickListener(this);

        if (moviesRV != null) {
            moviesRV.setLayoutManager(gridLayoutManager);
            moviesRV.setAdapter(movieAdapter);
        }
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

    @Override
    public void onItemClick(MovieModel movieModel) {
        Toast.makeText(this, movieModel.toString(), Toast.LENGTH_SHORT).show();
    }
}
