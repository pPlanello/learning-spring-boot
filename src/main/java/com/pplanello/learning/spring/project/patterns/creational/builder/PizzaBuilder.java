package com.pplanello.learning.spring.project.patterns.creational.builder;

import java.time.Duration;

/**
 * Builder is a creational design pattern that lets you construct complex objects step by step.
 * The pattern allows you to produce different types and representations of an object using the
 * same construction code.
 */
public class PizzaBuilder {

    public static PizzaBuilder newPizza(TypeDough dough) {
        return new PizzaBuilder(dough);
    }

    public PizzaBuilder with(String ingredient) {
        pizza.addIngredient(ingredient);
        return this;
    }

    public PizzaBuilder timeToMake(Duration duration) {
        pizza.setTimeToMake(duration);
        return this;
    }

    public Pizza build() {
        return pizza;
    }

    protected PizzaBuilder(TypeDough dough) {
        pizza = new Pizza(dough);
    }

    private final Pizza pizza;
}
