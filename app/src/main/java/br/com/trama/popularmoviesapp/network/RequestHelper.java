package br.com.trama.popularmoviesapp.network;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import br.com.trama.popularmoviesapp.util.Const;

/**
 * Created by trama on 28/06/16.
 */
public class RequestHelper {

    private static final String TAG = RequestHelper.class.getSimpleName();


    public String execute(Map<String, String> params) {

        String result = "";

        try {

            String method = params.remove(Const.Request.Method.TYPE);

            URL url = createURI(params);

            Log.d(TAG, url.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            result = convertStream(urlConnection.getInputStream());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return result;
    }

    @NonNull
    private URL createURI(Map<String, String> params) throws MalformedURLException {
        String baseUrl = params.remove(Const.Request.BASE_URL);

        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();

        Set<Map.Entry<String, String>> entries = params.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        Uri uri = builder.build();

        return new URL(uri.toString());
    }

    private String convertStream(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter(Const.Util.DELIMITER);
        return scanner.hasNext() ? scanner.next() : "";
    }
}
