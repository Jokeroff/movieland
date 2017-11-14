package com.lebediev.movieland.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;

import java.util.List;

public class MovieDto {
    @JsonView(MovieViews.BaseMovie.class)
    public int movieId;
    @JsonView(MovieViews.BaseMovie.class)
    public String movieNameRus;
    @JsonView(MovieViews.BaseMovie.class)
    public String movieNameNative;
    @JsonView(MovieViews.BaseMovie.class)
    public int date;
    @JsonView(MovieViews.ExtendedMovie.class)
    public String description;
    @JsonView(MovieViews.BaseMovie.class)
    public double rating;
    @JsonView(MovieViews.BaseMovie.class)
    public double price;
    @JsonView(MovieViews.ExtendedMovie.class)
    public List <Country> countries;
    @JsonView(MovieViews.ExtendedMovie.class)
    public List <Genre> genres;
    @JsonView(MovieViews.BaseMovie.class)
    public String poster;
    @JsonView(MovieViews.MovieWithReview.class)
    public List <ReviewDto> reviews;

    public MovieDto(int movieId, String movieNameRus, String movieNameNative, int date, String description, double rating, double price, List <Genre> genres, List <Country> countries, String poster, List <ReviewDto> reviews) {
        this.movieId = movieId;
        this.movieNameRus = movieNameRus;
        this.movieNameNative = movieNameNative;
        this.date = date;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.genres = genres;
        this.countries = countries;
        this.poster = poster;
        this.reviews = reviews;

    }

    public MovieDto() {
    }
}
