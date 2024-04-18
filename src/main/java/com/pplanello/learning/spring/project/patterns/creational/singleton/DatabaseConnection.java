package com.pplanello.learning.spring.project.patterns.creational.singleton;

/**
 * Singleton pattern ensures that a class has only one instance and provides a global point of access to it.
 * <p> The Singleton pattern solves two problems at the same time, violating the Single Responsibility Principle: </p>
 * 1. Ensure that a class has just a single instance.
 * 2. Provide a global point of access to that instance.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("DatabaseConnection constructor private to prevent instantiation from outside the class");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
