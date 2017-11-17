package com.lebediev.movieland.dao.jdbc.entity;

public enum OrderBy {
        RATING("RATING"),
        PRICE("PRICE");

        private final String name;

        OrderBy(String name) {
            this.name = name;
        }

        public static OrderBy getOrderBy(String name) {
            for (OrderBy orderBy : OrderBy.values()) {
                if (orderBy.name.equalsIgnoreCase(name)) {
                    return orderBy;
                }
            }
            throw new IllegalArgumentException("Wrong order by: " + name);
        }

}
