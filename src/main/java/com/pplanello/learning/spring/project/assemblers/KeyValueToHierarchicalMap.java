package com.pplanello.learning.spring.project.assemblers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * This class is responsible for transforming a flat map into a hierarchical map.
 * <p>
 * For example: {a.b.d=value2, a.b.c=1, a.e=3, f=4} -> {a={b={c=1, d=value2}, e=3}, f=4}
 */
public class KeyValueToHierarchicalMap implements UnaryOperator<Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Map<String, Object> flatMap) {
        return flatMap.entrySet().stream()
            .reduce(new HashMap<>(), this::accumulatorReduceFunction, (map1, map2) -> map1);
    }

    private HashMap<String, Object> accumulatorReduceFunction(HashMap<String, Object> accumulator,
                                                              Map.Entry<String, Object> entry) {
        var keys = entry.getKey().split(Pattern.quote("."));
        var currentLevel = (Map<String, Object>) accumulator;

        for (int i = 0; i < keys.length - 1; i++) {
            if (!currentLevel.containsKey(keys[i])) {
                currentLevel.put(keys[i], new HashMap<>());
            }
            currentLevel = (Map<String, Object>) currentLevel.get(keys[i]);
        }

        currentLevel.put(keys[keys.length - 1], entry.getValue());
        return accumulator;
    }

}
