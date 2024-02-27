package com.pplanello.learning.spring.project.patterns.behavioral.strategy;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Strategy is a behavioral design pattern that lets you define a family of algorithms,
 * put each of them into a separate class, and make their objects interchangeable.
 */
@Component
public class MathStrategyImpl implements TriFunction<Long, Long, String, Long> {

    @Override
    public Long apply(Long num1, Long num2, String operation) {
        return mathStrategies.stream()
            .filter(mathStrategy -> mathStrategy.isApplicable(operation))
            .map(mathStrategy -> mathStrategy.doOperation(num1, num2))
            .findFirst()
            .orElseThrow(() -> new NotImplementedException("Operation not implemented"));
    }

    void setMathStrategies(Collection<MathStrategy> mathStrategies) {
        this.mathStrategies = mathStrategies;
    }

    @Autowired
    private Collection<MathStrategy> mathStrategies;
}
