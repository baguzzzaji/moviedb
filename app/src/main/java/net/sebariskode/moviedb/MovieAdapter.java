package net.sebariskode.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by baguzzzaji on 3/27/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> movies;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Movie " + getPosition() + " clicked.");
                }
            });

            imageView = (ImageView) view.findViewById(R.id.movie_poster);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public MovieAdapter(ArrayList<Movie> moviesData) {
        movies = moviesData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Picasso.with(context).load(movies.get(position).moviePoster).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {

        if (movies == null) {
            return 0;
        }

        return movies.size();
    }
}
