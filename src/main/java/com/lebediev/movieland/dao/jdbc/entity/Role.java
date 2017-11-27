package com.lebediev.movieland.dao.jdbc.entity;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role getRole(String name) {
        for (Role role : Role.values()) {
            if (role.name.equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Wrong role: " + name);
    }

}
