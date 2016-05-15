package net.sebariskode.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    String movieTitle;
    String movieOverview;
    String movieReleaseDate;
    String moviePoster; // drawable reference id
    String movieBackdropImage;
    double movieRating;

    public Movie(String movie, String overview, double rating, String release_date, String image, String backdrop) {
        movieTitle = movie;
        moviePoster = image;
        movieOverview = overview;
        movieRating = rating;
        movieReleaseDate = release_date;
        movieBackdropImage = backdrop;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieBackdropImage() {
        return movieBackdropImage;
    }

    protected Movie(Parcel in) {
        movieTitle = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
        moviePoster = in.readString();
        movieBackdropImage = in.readString();
        movieRating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieOverview);
        dest.writeString(movieReleaseDate);
        dest.writeString(moviePoster);
        dest.writeString(movieBackdropImage);
        dest.writeDouble(movieRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}