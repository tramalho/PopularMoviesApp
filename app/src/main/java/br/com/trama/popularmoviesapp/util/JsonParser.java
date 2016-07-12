package br.com.trama.popularmoviesapp.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.model.MovieResponse;

/**
 * Created by trama on 28/06/16.
 */
public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    public MovieResponse fromStr(String result){
        MovieResponse movieResponse = new MovieResponse();

        try {
            JSONObject jsonObject = new JSONObject(result);
            int page       = jsonObject.getInt(Const.Json.PAGE);
            int totalPages = jsonObject.getInt(Const.Json.TOTAL_PAGES);

            movieResponse.setPage(page);
            movieResponse.setTotalPages(totalPages);

            JSONArray jsonArray = jsonObject.getJSONArray(Const.Json.RESULTS);
            int length = jsonArray.length();

            for(int i =0; i < length; i++){
                MovieModel m = parseMovie(jsonArray.getJSONObject(i));
                movieResponse.add(m);
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return movieResponse;
    }

    private MovieModel parseMovie(JSONObject jsonObject) throws JSONException {
        String posterPath    = jsonObject.getString(Const.Json.POSTER_PATH);
        String originalTitle = jsonObject.getString(Const.Json.ORIGINAL_TITLE);
        String overview      = jsonObject.getString(Const.Json.OVERVIEW);
        double voteAverage   = jsonObject.getDouble(Const.Json.VOTE_AVERAGE);
        String releaseStr    = jsonObject.getString(Const.Json.RELEASE_DATE);
        String backdropPath  = jsonObject.getString(Const.Json.BACKDROP_PATH);

        Calendar calendar = null;

        if(!TextUtils.isEmpty(releaseStr)){
            calendar = new DateHelper().strToCalendar(releaseStr, Const.Patterns.YMD_DATE);
        }

        return new MovieModel(posterPath, voteAverage, overview, calendar, originalTitle, backdropPath);
    }
}
