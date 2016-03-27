package net.sebariskode.moviedb;

public class Movie {
    String movieTitle;
    String movieOverview;
    double movieRating;
    String movieReleaseDate;
    int moviePoster; // drawable reference id

    public Movie(String movie, String overview, double rating, String release_date, int image) {
        movieTitle = movie;
        moviePoster = image;
        movieOverview = overview;
        movieRating = rating;
        movieReleaseDate = release_date;
    }
}
