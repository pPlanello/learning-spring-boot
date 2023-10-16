package com.pplanello.learning.spring.project.kafka;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TopicName {

    public Pattern getPattern() {
        return Pattern.compile(".*" + getName());
    }

    public String getName() {
        return TOPIC_NAME;
    }

    private static final String TOPIC_NAME = "topic-name";
}
