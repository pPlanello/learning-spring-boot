package com.pplanello.learning.spring.project.kafka;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TopicNameTest {

    @Test
    void it_should_return_the_expected_topic_name() {
        assertThat(topicName.getName())
                .as("It should return the expected topic name")
                .isEqualTo("topic-name");
    }

    @Test
    void it_should_return_the_expected_pattern_topic_name() {
        assertThat(topicName.getPattern())
                .as("It should return the expected pattern topic name")
                .hasToString(".*topic-name");
    }

    private final TopicName topicName = new TopicName();
}