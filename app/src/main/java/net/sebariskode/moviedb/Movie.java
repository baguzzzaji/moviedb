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
}
