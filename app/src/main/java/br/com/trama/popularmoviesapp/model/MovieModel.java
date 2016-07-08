package br.com.trama.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by trama on 28/06/16.
 */
public class MovieModel implements Parcelable {

    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private Calendar release;

    public MovieModel(String posterPath, double voteAverage,  String overview, Calendar release,
                      String originalTitle, String backdropPath) {

        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.release = release;
        this.backdropPath = backdropPath;
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

    public Calendar getRelease() {
        return release;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeDouble(this.voteAverage);
        dest.writeSerializable(this.release);
    }

    protected MovieModel(Parcel in) {
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readDouble();
        this.release = (Calendar) in.readSerializable();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
