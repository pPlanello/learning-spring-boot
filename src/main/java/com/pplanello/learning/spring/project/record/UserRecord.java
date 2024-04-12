package com.pplanello.learning.spring.project.record;

public record UserRecord(String id, String name, String email) {

    public UserRecord(String name, String email) {
        this(buildId(name, email), name, email);
    }

    public UserRecord {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Field email cannot be null or empty");
        }
    }

    private static String buildId(String name, String email) {
        return name + email;
    }
}
