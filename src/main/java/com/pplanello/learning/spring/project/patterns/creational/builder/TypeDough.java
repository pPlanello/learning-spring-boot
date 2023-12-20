package com.pplanello.learning.spring.project.patterns.creational.builder;

public enum TypeDough {
    FINA("fina"),
    THICK("thick"),
    WITH_CHEESE("with cheese");

    TypeDough(String dough) {
        this.dough = dough;
    }

    private String dough;
}
