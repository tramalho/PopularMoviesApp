package br.com.trama.popularmoviesapp.ui;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.math.BigDecimal;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView poster = (ImageView) findViewById(R.id.imageview_movie_detail_id);
        TextView releaseDate = (TextView) findViewById(R.id.textview_movie_detail_release_date_id);
        AppCompatSeekBar seekVoteAverage = (AppCompatSeekBar) findViewById(R.id.seekbar_movie_detail_vote_average_id);
        final TextView voteAverage = (TextView) findViewById(R.id.textview_movie_detail_vote_average_id);

        TextView overview = (TextView) findViewById(R.id.textview_movie_detail_overview_id);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(this.movieModel.getOriginalTitle());

        String yearRelease = new DateHelper()
                .calendarToStr(movieModel.getRelease(), Const.Patterns.YYYY);

        releaseDate.setText(yearRelease);
        int roundedVoteAverage = new BigDecimal("" + movieModel.getVoteAverage()).intValue();
        voteAverage.setText(getString(R.string.average_with_param, roundedVoteAverage));
        seekVoteAverage.setProgress(roundedVoteAverage);
        seekVoteAverage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String leftZero = "";
                if(progress < 10){
                    leftZero = "0";
                }
                voteAverage.setText(getString(R.string.average_with_param, leftZero + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        overview.setText(movieModel.getOverview());

        ChangeColorSimpleTarget changeColorSimpleTarget = new ChangeColorSimpleTarget();
        changeColorSimpleTarget.setCollapsingToolbarLayout(collapsingToolbarLayout);
        changeColorSimpleTarget.setOverview(overview);
        changeColorSimpleTarget.setPoster(poster);
        changeColorSimpleTarget.setReleaseDate(releaseDate);
        changeColorSimpleTarget.setSeekVoteAverage(seekVoteAverage);
        changeColorSimpleTarget.setSeekVoteAverage(voteAverage);

        Glide.with(poster.getContext())
                .load(String.format(Const.Url.IMDB_BASE_IMAGE_URL, this.movieModel.getBackdropPath()))
                .asBitmap()
                .placeholder(R.drawable.projetor)
                .into(changeColorSimpleTarget);
    }

    private class ChangeColorSimpleTarget extends SimpleTarget<Bitmap> {
        private ImageView poster;
        private CollapsingToolbarLayout collapsingToolbarLayout;

        private TextView releaseDate, overview, voteAverage;
        private AppCompatSeekBar seekVoteAverage;


        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
           poster.setImageBitmap(resource);
            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
                    int primary = getResources().getColor(R.color.colorPrimary);
                    int vibrantColor = palette.getVibrantColor(primary);
                    int darkVibrantColor = palette.getDarkVibrantColor(primaryDark);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(darkVibrantColor);
                    voteAverage.setTextColor(vibrantColor);
                    seekVoteAverage.getProgressDrawable().setColorFilter(vibrantColor, PorterDuff.Mode.SRC_IN);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekVoteAverage.getThumb().setColorFilter(vibrantColor, PorterDuff.Mode.SRC_IN);;
                    }
                    releaseDate.setTextColor(vibrantColor);
                    overview.setTextColor(vibrantColor);
                }
            });
        }

        public void setPoster(ImageView poster) {
            this.poster = poster;
        }

        public void setCollapsingToolbarLayout(CollapsingToolbarLayout collapsingToolbarLayout) {
            this.collapsingToolbarLayout = collapsingToolbarLayout;
        }

        public void setReleaseDate(TextView releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setSeekVoteAverage(AppCompatSeekBar voteAverage) {
            this.seekVoteAverage = voteAverage;
        }

        public void setOverview(TextView overview) {
            this.overview = overview;
        }

        public void setSeekVoteAverage(TextView voteAverage) {
            this.voteAverage = voteAverage;
        }
    }
}
