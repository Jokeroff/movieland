package com.lebediev.movieland.entity;

public class Country {
    private int countryId;
    private String countryName;

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }


    @Override
    public String toString() {
        return "Country{" +
               "countryId=" + countryId +
               ", countryName='" + countryName + '\'' +
               '}';
    }
}
