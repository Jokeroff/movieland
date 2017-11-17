package com.lebediev.movieland.dao.jdbc.entity;

public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    public static SortDirection getDirection(String name) {
        for (SortDirection sortDirection : SortDirection.values()) {
            if (sortDirection.name.equalsIgnoreCase(name)) {
                return sortDirection;
            }
        }
        throw new IllegalArgumentException("Wrong sort direction: " + name);
    }
}
