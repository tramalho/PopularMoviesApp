package br.com.trama.popularmoviesapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.trama.popularmoviesapp.R;
import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.util.Const;
import br.com.trama.popularmoviesapp.util.DateHelper;

public class DetailActivity extends AppCompatActivity {

    private MovieModel movieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.movieModel = getIntent().getParcelableExtra(Const.Param.MOVIE_MODEL);

        if(this.movieModel == null){
            throw new IllegalArgumentException("no data in bundle");
        }

        loadUI();
    }

    private void loadUI() {
        ImageView poster = (ImageView) findViewById(R.id.imageview_movie_detail_id);
        TextView releaseDate = (TextView) findViewById(R.id.textview_movie_detail_release_date_id);
        //TextView time = (TextView) findViewById(R.id.textview_movie_detail_time_id);
        TextView voteAverage = (TextView) findViewById(R.id.textview_movie_detail_vote_average_id);
        TextView overview = (TextView) findViewById(R.id.textview_movie_detail_overview_id);

        String yearRelease = new DateHelper()
                .calendarToStr(movieModel.getRelease(), Const.Patterns.YYYY);

        releaseDate.setText(yearRelease);

        voteAverage.setText(getString(R.string.average_with_param, movieModel.getVoteAverage()));

        overview.setText(movieModel.getOverview());

        Glide.with(poster.getContext())
                .load(String.format(Const.Url.IMDB_BASE_IMAGE_URL, this.movieModel.getPosterPath()))
                .placeholder(R.drawable.projetor)
                .into(poster);
    }
}
