package net.sebariskode.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private final static String TAG = DetailActivityFragment.class.getSimpleName();

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView backdropImageView = (ImageView) rootView.findViewById(R.id.movie_backdrop);
        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.detail_poster);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.detail_movie_title);
        TextView releasedDateTextView = (TextView) rootView.findViewById(R.id.detail_movie_released_date);
        TextView ratingTextView = (TextView) rootView.findViewById(R.id.detail_movie_vote_average);
        TextView overviewTextView = (TextView) rootView.findViewById(R.id.detail_movie_overview);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Intent intent = getActivity().getIntent();

        String backdrop = intent.getStringExtra(MovieAdapter.ViewHolder.MOVIE_BACKDROP);
        String poster = intent.getStringExtra(MovieAdapter.ViewHolder.MOVIE_POSTER);
        String overview = intent.getStringExtra(MovieAdapter.ViewHolder.MOVIE_OVERVIEW);
        String title = intent.getStringExtra(MovieAdapter.ViewHolder.MOVIE_TITLE);
        String released_date = intent.getStringExtra(MovieAdapter.ViewHolder.MOVIE_RELEASE_DATE);
        double rating = intent.getDoubleExtra(MovieAdapter.ViewHolder.MOVIE_RATING, 0);

        String backdrop_url = "http://image.tmdb.org/t/p/w342" + backdrop;
        String poster_url = "http://image.tmdb.org/t/p/w185" + poster;

        Picasso.with(getActivity()).load(backdrop_url).into(backdropImageView);
        Picasso.with(getActivity()).load(poster_url).into(posterImageView);


        titleTextView.setText(title);
        ratingTextView.setText(String.valueOf(rating));
        overviewTextView.setText(overview);
        // Date format
        try {
            SimpleDateFormat curFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = curFormat.parse(released_date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            releasedDateTextView.setText(simpleDateFormat.format(date));
            Log.d(TAG, simpleDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        return rootView;
    }
}
