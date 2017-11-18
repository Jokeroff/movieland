package com.lebediev.movieland.entity;

public class Country {
    private int id;
    private String name;

    public Country(int id, String countryName) {
        this.id = id;
        this.name = countryName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Country{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
