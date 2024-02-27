package com.pplanello.learning.spring.project.patterns.behavioral.strategy;


import java.util.Optional;

public interface MathStrategy {

    Boolean isApplicable(String operation);

    Long doOperation(Long num1, Long num2);

}
