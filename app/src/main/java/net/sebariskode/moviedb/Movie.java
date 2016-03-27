package net.sebariskode.moviedb;

public class Movie {
    String movieTitle;
    String movieOverview;
    double movieRating;
    String movieReleaseDate;
    String moviePoster; // drawable reference id

    public Movie(String movie, String overview, double rating, String release_date, String image) {
        movieTitle = movie;
        moviePoster = image;
        movieOverview = overview;
        movieRating = rating;
        movieReleaseDate = release_date;
    }
}
