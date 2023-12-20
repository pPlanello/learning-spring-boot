package com.pplanello.learning.spring.project.patterns.creational.builder;

import org.junit.jupiter.api.Test;

import static com.pplanello.learning.spring.project.patterns.creational.builder.PizzaBuilder.newPizza;
import static com.pplanello.learning.spring.project.patterns.creational.builder.TypeDough.FINA;
import static org.assertj.core.api.Assertions.assertThat;

class PizzaBuilderTest {

    @Test
    void it_should_build_a_pizza_with_dough_type_fina() {
        var pizza = newPizza(FINA).build();

        assertThat(pizza.getDough())
            .as("It should build a pizza with dough type fina")
            .isEqualTo(FINA);
    }

    @Test
    void it_should_build_a_pizza_with_dough_type_fina_with_cheese() {
        var pizza = newPizza(FINA)
            .with("cheese")
            .build();

        assertThat(pizza.findIngredient("cheese"))
            .as("It should build a pizza with dough type fina")
            .contains("cheese");
    }



    @Test
    void it_should_build_a_pizza_with_dough_type_fina_with_cheese_and_tomato() {
        var pizza = newPizza(FINA)
            .with("cheese")
            .with("tomato")
            .build();

        assertThat(pizza.getIngredients())
            .as("It should build a pizza with dough type fina")
            .contains("cheese")
            .contains("tomato");
    }
}