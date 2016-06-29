package br.com.trama.popularmoviesapp.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.com.trama.popularmoviesapp.BuildConfig;
import br.com.trama.popularmoviesapp.util.Const;

/**
 * Created by trama on 28/06/16.
 */
public class RequestHelper {

    private static final String TAG = RequestHelper.class.getSimpleName();


    public String execute(String urlbase, String method) {

        String result = "";

        try {

            Uri uri = Uri.parse(urlbase).buildUpon()
                    .appendQueryParameter(Const.Url.APP_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            Log.d(TAG, uri.toString());

            URL url = new URL(uri.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            result = new Scanner(inputStream).next();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return result;
    }
}
