package br.com.trama.popularmoviesapp.model;

/**
 * Created by trama on 28/06/16.
 */
public class MovieModel {

    private String posterPath;

    public MovieModel(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
