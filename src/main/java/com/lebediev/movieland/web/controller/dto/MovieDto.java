package com.lebediev.movieland.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;

import java.util.List;

public class MovieDto {
    @JsonView(MovieViews.BaseMovie.class)
    public int id;
    @JsonView(MovieViews.BaseMovie.class)
    public String nameRussian;
    @JsonView(MovieViews.BaseMovie.class)
    public String nameNative;
    @JsonView(MovieViews.BaseMovie.class)
    public int yearOfRelease;
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
    public String picturePath;
    @JsonView(MovieViews.MovieWithReview.class)
    public List <ReviewDto> reviews;

    public MovieDto(int id, String nameRussian, String nameNative, int yearOfRelease, String description, double rating, double price, List <Genre> genres, List <Country> countries, String picturePath, List <ReviewDto> reviews) {
        this.id = id;
        this.nameRussian = nameRussian;
        this.nameNative = nameNative;
        this.yearOfRelease = yearOfRelease;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.genres = genres;
        this.countries = countries;
        this.picturePath = picturePath;
        this.reviews = reviews;

    }

    public MovieDto() {
    }
}
