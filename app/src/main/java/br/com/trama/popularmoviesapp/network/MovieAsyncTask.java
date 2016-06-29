package br.com.trama.popularmoviesapp.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.Collections;
import java.util.List;

import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.util.Const;
import br.com.trama.popularmoviesapp.util.JsonParser;

/**
 * Created by trama on 28/06/16.
 */
public class MovieAsyncTask extends AsyncTask<Void, Void, List<MovieModel>> {

    private static final String TAG = MovieAsyncTask.class.getSimpleName();
    private int page;

    public interface MovieAsyncTaskCallback {
        void onResult(List<MovieModel> movieModels);
    }

    private MovieAsyncTaskCallback callback;
    private RequestHelper requestHelper;
    private JsonParser jsonParser;

    public MovieAsyncTask(MovieAsyncTaskCallback callback, int page) {
        this(callback, new RequestHelper(), new JsonParser());
        this.page = page;
    }

    public MovieAsyncTask(MovieAsyncTaskCallback callback, RequestHelper requestHelper, JsonParser jsonParser) {
        this.callback = callback;
        this.requestHelper = requestHelper;
        this.jsonParser = jsonParser;
    }

    @Override
    protected List<MovieModel> doInBackground(Void... params) {

        List<MovieModel> movies = Collections.emptyList();

        StringBuilder builder = new StringBuilder(Const.Url.IMDB_BASE_URL);
        builder.append(Const.Url.END_POINT_POPULAR);

        if(this.page > 0){
            builder.append(Const.Url.QUERY_PAGE)
                    .append(page);
        }

        String result = requestHelper.execute(builder.toString(), Const.Request.Method.GET);

        if(!TextUtils.isEmpty(result)){
            movies = jsonParser.fromStr(result);
        }

        return movies;
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        callback.onResult(movieModels);
    }
}
