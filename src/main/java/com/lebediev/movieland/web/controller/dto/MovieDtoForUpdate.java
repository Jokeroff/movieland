package com.lebediev.movieland.web.controller.dto;

import java.util.Arrays;
import java.util.List;

public class MovieDtoForUpdate {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private int[] countries;
    private int[] genres;
    private String picturePath;
    private List <ReviewDto> reviews;

    public MovieDtoForUpdate(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCountries(int[] countries) {
        this.countries = countries;
    }

    public void setGenres(int[] genres) {
        this.genres = genres;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setReviews(List <ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int[] getCountries() {
        return countries;
    }

    public int[] getGenres() {
        return genres;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public List <ReviewDto> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "MovieDtoForUpdate{" +
               "nameRussian='" + nameRussian + '\'' +
               ", nameNative='" + nameNative + '\'' +
               ", yearOfRelease=" + yearOfRelease +
               ", description='" + description + '\'' +
               ", rating=" + rating +
               ", price=" + price +
               ", countries=" + Arrays.toString(countries) +
               ", genres=" + Arrays.toString(genres) +
               ", picturePath='" + picturePath + '\'' +
               ", reviews=" + reviews +
               '}';
    }
}
