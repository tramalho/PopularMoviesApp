package br.com.trama.popularmoviesapp.model;

/**
 * Created by trama on 28/06/16.
 */
public class MovieModel {

    private String posterPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;

    public MovieModel(String posterPath, String originalTitle, String overview, double voteAverage) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
