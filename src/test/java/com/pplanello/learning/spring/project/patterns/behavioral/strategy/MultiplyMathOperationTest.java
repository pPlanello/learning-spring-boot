package com.pplanello.learning.spring.project.patterns.behavioral.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MultiplyMathOperationTest {

    @Test
    void it_should_return_true_when_is_multiply_operation() {
        assertThat(multiplyMathOperation.isApplicable(multiply_OPERATION))
            .as("It should return true when is multiply operation")
            .isTrue();
    }

    @Test
    void it_should_return_false_when_is_other_operation() {
        assertThat(multiplyMathOperation.isApplicable(OTHER_OPERATION))
            .as("It should return false when is other operation")
            .isFalse();
    }

    @MethodSource("mathOperationProvider")
    @ParameterizedTest(name = "It should return {2} value when do operation {0} * {1}")
    void it_should_return_expected_value_when_do_operation(Long value1, Long value2, Long expected) {
        assertThat(multiplyMathOperation.doOperation(value1, value2))
            .as("It should return {2} value when do operation {0} * {1}")
            .isEqualTo(expected);
    }

    private static Stream<Arguments> mathOperationProvider() {
        return Stream.of(
            Arguments.of(1L, 2L, 2L),
            Arguments.of(2L, 3L, 6L),
            Arguments.of(3L, 4L, 12L)
        );
    }

    private final MultiplyMathOperation multiplyMathOperation = new MultiplyMathOperation();

    private static final String multiply_OPERATION = "multiply";
    private static final String OTHER_OPERATION = "other";
}