package com.pplanello.learning.spring.project.assemblers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class KeyValueToHierarchicalMapTest {

    @Test
    void it_should_return_not_null() {

        assertThat(keyValueToHierarchicalMap.apply(Map.of()))
            .as("It should return not null")
            .isNotNull();
    }

    @MethodSource("provideParameters")
    @ParameterizedTest(name = "it should return the expected map: {1}")
    public void test(Map<String, Object> in, Map<String, Object> expected) {
        assertThat(keyValueToHierarchicalMap.apply(in))
            .as("It should return the expected map: %s", expected)
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
            Arguments.of(in_test_1, expected_test_1),
            Arguments.of(in_test_2, expected_test_2)
        );
    }

    private KeyValueToHierarchicalMap keyValueToHierarchicalMap = new KeyValueToHierarchicalMap();

    private static final Map<String, Object> in_test_1 = Map.of(
        "a.b.c", 1,
        "a.b.d", "value2",
        "a.e", 3,
        "f", 4
    );
    private static final Map<String, Object> expected_test_1 = Map.of(
        "a", Map.of(
            "b", Map.of(
                "c", 1,
                "d", "value2"
            ),
            "e", 3
        ),
        "f", 4
    );
    private static final Map<String, Object> in_test_2 = Map.of(
        "a.b.e", 1,
        "a.c.d", "value2",
        "a.c.f", 3,
        "g.h", 4
    );
    private static final Map<String, Object> expected_test_2 = Map.of(
        "a", Map.of(
            "b", Map.of(
                "e", 1
            ),
            "c", Map.of(
                "d", "value2",
                "f", 3
            )
        ),
        "g", Map.of(
            "h", 4
        )
    );

}