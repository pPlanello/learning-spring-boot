package com.pplanello.learning.spring.project.patterns.behavioral.strategy;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.stream.Stream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathStrategyImplTest {

    @Test
    void it_should_return_not_null() {
        mockAddOperation(1L, 2L, 3L);

        assertThat(mathStrategyImpl.apply(1L, 2L, ADD_OPERATION))
            .as("It should return not null")
            .isNotNull();
    }

    @Test
    void it_should_return_expected_value_when_add_operation() {
        mockAddOperation(1L, 2L, 3L);

        assertThat(mathStrategyImpl.apply(1L, 2L, ADD_OPERATION))
            .as("It should return expected value when add operation")
            .isEqualTo(3L);
    }

    @Test
    void it_should_return_error_when_operation_is_not_implemented() {
        mockMathNotImplemented();

        assertThatThrownBy(() -> mathStrategyImpl.apply(1L, 2L, ADD_OPERATION))
            .as("It should return error when operation is not implemented")
            .isInstanceOf(NotImplementedException.class)
            .hasMessage("Operation not implemented");
    }

    private void mockAddOperation(Long num1, Long num2, Long result) {
        when(addOperation.isApplicable(ADD_OPERATION)).thenReturn(TRUE);
        when(addOperation.doOperation(num1, num2)).thenReturn(result);
    }

    private void mockMathNotImplemented() {
        when(addOperation.isApplicable(anyString())).thenReturn(FALSE);
        when(mathStrategy2.isApplicable(anyString())).thenReturn(FALSE);
    }

    @BeforeEach()
    void setup() {
        when(mathStrategies.stream()).thenReturn(Stream.of(addOperation, mathStrategy2));

        mathStrategyImpl = new MathStrategyImpl();
        mathStrategyImpl.setMathStrategies(mathStrategies);
    }

    private MathStrategyImpl mathStrategyImpl;

    @Mock
    private Collection<MathStrategy> mathStrategies;
    @Mock
    private MathStrategy addOperation;
    @Mock
    private MathStrategy mathStrategy2;

    private static final String ADD_OPERATION = "add";
}