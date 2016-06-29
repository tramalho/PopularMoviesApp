package br.com.trama.popularmoviesapp.util;

/**
 * Created by trama on 28/06/16.
 */
public interface Const {

    interface Url {
        String IMDB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/%1s";
        String IMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
        String END_POINT_POPULAR = "popular";
        String APP_KEY = "api_key";
        String QUERY_PAGE = "&page=";
    }

    interface Request {

        interface Method {
            String GET = "GET";
        }
    }
}
