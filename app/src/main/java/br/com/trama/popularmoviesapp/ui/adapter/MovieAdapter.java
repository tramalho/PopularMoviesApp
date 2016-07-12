package br.com.trama.popularmoviesapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.trama.popularmoviesapp.R;
import br.com.trama.popularmoviesapp.model.MovieModel;
import br.com.trama.popularmoviesapp.util.Const;


/**
 * Created by trama on 27/06/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieModel> moviesList;
    private MovieAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(MovieModel movieModel);
    }

    public MovieAdapter(List<MovieModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pop_movies, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        MovieModel movie = this.moviesList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }

    public void setOnItemClickListener(MovieAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pop_imageview_id);
            itemView.setOnClickListener(this);
        }

        public void bind(MovieModel movie) {

            Glide.clear(imageView);
            Glide.with(imageView.getContext())
                    .load(String.format(Const.Url.IMDB_BASE_IMAGE_URL, movie.getPosterPath()))
                    .placeholder(R.drawable.projetor)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
            }
        }
    }

}
