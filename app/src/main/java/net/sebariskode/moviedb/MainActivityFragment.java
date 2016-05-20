package net.sebariskode.moviedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    protected RecyclerView recyclerView;

    protected GridLayoutManager gridLayoutManager;

    protected MovieAdapter movieAdapter;


    protected ArrayList<Movie> movies = new ArrayList<>();

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            if (isNetworkAvailable()) {
                getMovies();
            } else {
                Toast.makeText(getActivity(), "Could not download movies, network error.", Toast.LENGTH_SHORT).show();
            }
        } else {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movies);
        super.onSaveInstanceState(outState);
    }

    public void getMovies() {

        final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
        // Get Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String TYPE_PATH = preferences.getString(getString(R.string.pref_movies_key),
                getString(R.string.pref_movies_key_default));
        Log.v(TAG, TYPE_PATH);
        // Get Movies Data
        final String API_KEY = getString(R.string.api_key);
        String full_url = MOVIES_BASE_URL + TYPE_PATH + "?api_key=" + API_KEY;
        Log.v(TAG, full_url);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, full_url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray moviesArray = response.getJSONArray("results");

                            for (int i = 0; i < moviesArray.length(); i++) {
                                JSONObject movieJson = moviesArray.getJSONObject(i);

                                String title = movieJson.getString("original_title");
                                String poster = "http://image.tmdb.org/t/p/w185" + movieJson.getString("poster_path");
                                String backdrop = movieJson.getString("backdrop_path");
                                String overview = movieJson.getString("overview");
                                double rating = movieJson.getDouble("vote_average");
                                String release_date = movieJson.getString("release_date");

                                movies.add(new Movie(title, overview, rating, release_date, poster, backdrop));
                                if (movieAdapter != null) {
                                    MovieAdapter newMovieAdapter = new MovieAdapter(getActivity(), movies);
                                    recyclerView.setAdapter(newMovieAdapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Download error!", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(getActivity()).add(jsonRequest);
        Toast.makeText(getContext(), "Download success!", Toast.LENGTH_SHORT).show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        if (movies != null) {
                            movies.clear();
                            if (isNetworkAvailable()) {
                                getMovies();
                            } else {
                                Toast.makeText(getActivity(), "Could not download movies, network error.", Toast.LENGTH_SHORT).show();
                            }
                            //movieAdapter.onDataChanged(movies);
                            //Log.v(TAG, "Adapter notified");
                        }
                    }
                };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.movies_grid);
        gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        movieAdapter = new MovieAdapter(getContext(), movies);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);

        return rootView;
    }

}
