package com.lebediev.movieland.entity;

import java.util.List;

public class Movie {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private List <Genre> genres;
    private List <Country> countries;
    private String picturePath;
    private List <Review> reviews;

    public String getPicturePath() {
        return picturePath;
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

    public List <Genre> getGenres() {
        return genres;
    }

    public List <Country> getCountries() {
        return countries;
    }

    public List <Review> getReviews() {
        return reviews;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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

    public void setGenres(List <Genre> genres) {
        this.genres = genres;
    }

    public void setCountries(List <Country> countries) {
        this.countries = countries;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReviews(List <Review> reviews) {
        this.reviews = reviews;
    }

    public Movie(int id, String nameRussian, String nameNative, int yearOfRelease, String description, double rating, double price, List <Genre> genres, List <Country> countries, String picturePath, List <Review> reviews) {
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

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", nameRussian='" + nameRussian + '\'' +
               ", nameNative='" + nameNative + '\'' +
               ", yearOfRelease=" + yearOfRelease +
               ", description='" + description + '\'' +
               ", rating=" + rating +
               ", price=" + price +
               ", genres=" + genres +
               ", countries=" + countries +
               ", picturePath='" + picturePath + '\'' +
               ", reviews=" + reviews +
               '}';
    }
}
