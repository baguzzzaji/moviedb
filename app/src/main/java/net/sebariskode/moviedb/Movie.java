package net.sebariskode.moviedb;

public class Movie {
    String movieTitle;

    String movieOverview;

    double movieRating;

    String movieReleaseDate;

    String moviePoster; // drawable reference id

    String movieBackdropImage;

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
}
