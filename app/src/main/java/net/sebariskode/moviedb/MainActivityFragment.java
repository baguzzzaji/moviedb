package net.sebariskode.moviedb;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by baguzzzaji on 3/27/16.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;
    private MovieAdapter movieAdapter;
    protected RecyclerView recyclerView;
    protected GridLayoutManager gridLayoutManager;
    protected ArrayList<Movie> movies;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchMoviesTask task = new FetchMoviesTask();
        task.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.movies_grid);
        gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        movieAdapter = new MovieAdapter(movies);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;

            String api_key = getContext().getString(R.string.api_key);

            try {
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie";
                final String TYPE_PATH = "popular";
                final String KEY_PARAM = "api_key";

                Uri builUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendPath(TYPE_PATH)
                        .appendQueryParameter(KEY_PARAM, api_key)
                        .build();

                try {
                    URL url = new URL(builUri.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error ", e);
                }

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

                try {
                    return getMovieDataFromJson(moviesJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }
            }
            return null;
        }

        private ArrayList<Movie> getMovieDataFromJson(String moviesJsonStr) throws JSONException {
            // Values we take
            String title;
            String poster;
            String overview;
            double rating;
            String release_date;
            String backdrop;

            // Server movies information
            final String TITLE = "original_title";
            final String POSTER = "poster_path";
            final String OVERVIEW = "overview";
            final String RATING = "vote_average";
            final String RELEASE_DATE = "release_date";
            final String POSTER_BASE = "http://image.tmdb.org/t/p/w185";

            // Json information
            final String RESULTS = "results";

            try {
                JSONObject moviesJson = new JSONObject(moviesJsonStr);
                JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);

                Log.v(TAG, moviesJson.toString());
                Log.v(TAG, moviesArray.toString());

                movies = new ArrayList<>();

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieJson = moviesArray.getJSONObject(i);

                    title = movieJson.getString(TITLE);
                    poster = movieJson.getString(POSTER);
                    overview = movieJson.getString(OVERVIEW);
                    rating = movieJson.getDouble(RATING);
                    release_date = movieJson.getString(RELEASE_DATE);


                    movies.add(new Movie(title, overview, rating, release_date, POSTER_BASE+poster, "backdrop_path"));
                }

                return movies;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesData) {
            super.onPostExecute(moviesData);
            movieAdapter.notifyDataSetChanged();
        }
    }
}
