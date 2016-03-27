package net.sebariskode.moviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

/**
 * Created by baguzzzaji on 3/27/16.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    Movie[] movies = {
            new Movie("Deadpool", "Yeah", 8.9, "012816", R.drawable.poster),
            new Movie("Superman", "Son of Krypton", 9.0, "022416", R.drawable.poster),
            new Movie("Batman", "King of Gotham", 9.5, "032416", R.drawable.poster),
            new Movie("Avenger", "So so", 8.0, "031616", R.drawable.poster)
    };

    public MainActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;
    }
}
