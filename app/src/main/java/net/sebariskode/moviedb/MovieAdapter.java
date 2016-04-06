package net.sebariskode.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> movies;

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        static final String MOVIE_TITLE = "movie_title";

        static final String MOVIE_OVERVIEW = "movie_overview";

        static final String MOVIE_RELEASE_DATE = "movie_release_date";

        static final String MOVIE_POSTER = "movie_poster";

        static final String MOVIE_BACKDROP = "movie_backdrop";

        static final String MOVIE_RATING = "movie_rating";

        private final ImageView imageView;

        private final Context context;

        public ViewHolder(final View view, final ArrayList<Movie> movies) {
            super(view);
            context = view.getContext();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(MOVIE_TITLE, movies.get(getAdapterPosition()).getMovieTitle());
                    intent.putExtra(MOVIE_OVERVIEW, movies.get(getAdapterPosition()).getMovieOverview());
                    intent.putExtra(MOVIE_RELEASE_DATE, movies.get(getAdapterPosition()).getMovieReleaseDate());
                    intent.putExtra(MOVIE_POSTER, movies.get(getAdapterPosition()).getMoviePoster());
                    intent.putExtra(MOVIE_BACKDROP, movies.get(getAdapterPosition()).getMovieBackdropImage());
                    intent.putExtra(MOVIE_RATING, movies.get(getAdapterPosition()).getMovieRating());
                    context.startActivity(intent);
                    Log.d(TAG, "Movie " + getAdapterPosition() + " clicked.");
                }
            });


            imageView = (ImageView) view.findViewById(R.id.movie_poster);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public MovieAdapter(Context parent_context, ArrayList<Movie> moviesData) {
        movies = moviesData;
        context = parent_context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view, movies);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(movies.get(position).moviePoster).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
