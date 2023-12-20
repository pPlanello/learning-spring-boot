package com.pplanello.learning.spring.project.patterns.creational.builder;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.pplanello.learning.spring.project.patterns.creational.builder.PizzaBuilder.newPizza;
import static com.pplanello.learning.spring.project.patterns.creational.builder.TypeDough.*;
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
    void it_should_build_a_pizza_with_dough_type_thick_without_ingredients() {
        var pizza = newPizza(THICK).build();

        assertThat(pizza.getIngredients())
            .as("It should build a pizza with thick without ingredients")
            .isEmpty();
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

    @Test
    void it_should_build_a_pizza_with_dough_type_cheese_with_default_time_to_make() {
        var pizza = newPizza(WITH_CHEESE)
            .build();

        assertThat(pizza.getTimeToMake())
            .as("It should build a pizza with dough type cheese with default time to make")
            .isEqualTo(Duration.ofMinutes(DEFAULT_TIME));
    }

    @Test
    void it_should_build_a_pizza_with_dough_type_cheese_with_specific_time_to_make() {
        var pizza = newPizza(WITH_CHEESE)
            .timeToMake(SPECIFIC_TIME)
            .build();

        assertThat(pizza.getTimeToMake())
            .as("It should build a pizza with dough type cheese with specific time to make")
            .isEqualTo(SPECIFIC_TIME);
    }

    private static final Integer DEFAULT_TIME = 10;
    private static final Duration SPECIFIC_TIME = Duration.ofMinutes(7);

}