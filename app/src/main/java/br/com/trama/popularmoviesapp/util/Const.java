package br.com.trama.popularmoviesapp.util;

/**
 * Created by trama on 28/06/16.
 */
public interface Const {

    interface Url {
        String IMDB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/%1s";
        String IMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
        String END_POINT_POPULAR = "popular";
        String QUERY_PAGE = "page";
        String APP_KEY = "api_key";
    }

    interface Request {

        String BASE_URL = "base_url";

        interface Method {
            String GET = "GET";
            String TYPE = "type";
        }
    }

    interface Json {
        String RESULTS = "results";
        String PAGE = "page";
        String POSTER_PATH = "poster_path";
        String ORIGINAL_TITLE = "original_title";
        String OVERVIEW = "overview";
        String VOTE_AVERAGE = "vote_average";
    }

    interface Util {
        String DELIMITER = "\\A";
        int SPAN_COLUMNS = 2;
    }
}
