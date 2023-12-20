package com.pplanello.learning.spring.project.patterns.creational.builder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pizza {

    public Pizza(TypeDough dough) {
        this.dough = dough;
        this.ingredients = new ArrayList<>();
        this.timeToMake = Duration.ofMinutes(DEFAULT_TIME);
    }

    public TypeDough getDough() {
        return dough;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public Optional<String> findIngredient(String ingredient) {
        return this.ingredients.stream().filter(ingredient::equals).findFirst();
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }


    public Duration getTimeToMake() {
        return this.timeToMake;
    }

    public void setTimeToMake(Duration duration) {
        this.timeToMake = duration;
    }

    private final TypeDough dough;
    private List<String> ingredients;
    private Duration timeToMake;
    private static final int DEFAULT_TIME = 10;
}
