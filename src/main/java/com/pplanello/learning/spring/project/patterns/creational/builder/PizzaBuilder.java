package com.pplanello.learning.spring.project.patterns.creational.builder;

public class PizzaBuilder {

    public static PizzaBuilder newPizza(TypeDough dough) {
        return new PizzaBuilder(dough);
    }

    public PizzaBuilder with(String ingredient) {
        pizza.addIngredient(ingredient);
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
