package br.com.trama.popularmoviesapp.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import br.com.trama.popularmoviesapp.BuildConfig;
import br.com.trama.popularmoviesapp.model.MovieResponse;
import br.com.trama.popularmoviesapp.util.Const;
import br.com.trama.popularmoviesapp.util.JsonParser;

/**
 * Created by trama on 28/06/16.
 */
public class MovieAsyncTask extends AsyncTask<Void, Void, MovieResponse> {

    private int page;

    public interface MovieAsyncTaskCallback {
        void onResult(MovieResponse movieModels);

        boolean isPopularSort(String sort);
    }

    private MovieAsyncTaskCallback callback;
    private RequestHelper requestHelper;
    private JsonParser jsonParser;
    private String sort;

    public MovieAsyncTask(MovieAsyncTaskCallback callback, int page, String sort) {
        this(callback, new RequestHelper(), new JsonParser(), sort);
        this.page = page;
    }

    public MovieAsyncTask(MovieAsyncTaskCallback callback, RequestHelper requestHelper,
                          JsonParser jsonParser, String sort) {
        this.callback = callback;
        this.requestHelper = requestHelper;
        this.jsonParser = jsonParser;
        this.sort = sort;
    }

    @Override
    protected MovieResponse doInBackground(Void... params) {

        MovieResponse movieResponse = new MovieResponse();

        String endPoint = this.callback.isPopularSort(this.sort)
                ? Const.Url.END_POINT_POPULAR : Const.Url.END_POINT_TOP_RATED;

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(Const.Request.BASE_URL, Const.Url.IMDB_BASE_URL + endPoint);
        requestParams.put(Const.Url.QUERY_PAGE, ""+this.page);
        requestParams.put(Const.Url.APP_KEY, BuildConfig.THE_MOVIE_DB_API_KEY);
        requestParams.put(Const.Request.Method.TYPE, Const.Request.Method.GET);

        String result = requestHelper.execute(requestParams);

        if(!TextUtils.isEmpty(result)){
            movieResponse = jsonParser.fromStr(result);
        }

        return movieResponse;
    }

    @Override
    protected void onPostExecute(MovieResponse movieResponse) {
        callback.onResult(movieResponse);
    }
}
