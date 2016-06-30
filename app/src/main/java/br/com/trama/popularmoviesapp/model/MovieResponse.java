package br.com.trama.popularmoviesapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trama on 30/06/16.
 */
public class MovieResponse {
    private List<MovieModel> movieModels;
    private int page;

    public MovieResponse() {
        this.movieModels = new ArrayList<>();
    }

    public void add(MovieModel m) {
        this.movieModels.add(m);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }
}
