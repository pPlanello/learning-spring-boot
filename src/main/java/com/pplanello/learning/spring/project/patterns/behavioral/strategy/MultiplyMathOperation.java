package com.pplanello.learning.spring.project.patterns.behavioral.strategy;

import org.springframework.stereotype.Component;

@Component
public class MultiplyMathOperation implements MathStrategy {

    @Override
    public Boolean isApplicable(String operation) {
        return OPERATION.equals(operation);
    }

    @Override
    public Long doOperation(Long num1, Long num2) {
        return num1 * num2;
    }

    private static final String OPERATION = "multiply";
}
