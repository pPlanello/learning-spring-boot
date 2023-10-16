package com.pplanello.learning.spring.project.kafka;

import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaConfigurationTest {

    @Test
    void it_should_have_enable_auto_commit_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the enable auto commit configuration")
                .containsEntry("enable.auto.commit", "false");
    }

    @Test
    void it_should_have_bootstrap_servers_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the bootstrap servers configuration")
                .containsEntry("bootstrap.servers", KAFKA_BROKER_CONNECTION);
    }

    @Test
    void it_should_have_group_id_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the group id configuration")
                .containsEntry("group.id", TOPIC_NAME_CONSUMER);
    }

    @Test
    void it_should_have_client_id_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the client id configuration")
                .containsEntry("client.id", TOPIC_NAME_CONSUMER);
    }

    @Test
    void it_should_have_key_deserializer_class_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the key deserializer class configuration")
                .containsEntry("key.deserializer", IntegerDeserializer.class);
    }

    @Test
    void it_should_have_value_deserializer_class_configuration() {
        assertThat(kafkaConfiguration.kafkaReceiverOptions().consumerProperties())
                .as("It should have the value deserializer class configuration")
                .containsEntry("value.deserializer", MapDeserializer.class);
    }

    @BeforeEach
    void setupKafkaBrokerConnection() {
        kafkaConfiguration.setKafkaBrokerConnection(KAFKA_BROKER_CONNECTION);
    }

    @BeforeEach
    void setupKafkaTopic() {
        when(topicName.getName()).thenReturn(TOPIC_NAME);
        when(topicName.getPattern()).thenReturn(TOPIC_NAME_PATTERN);
    }

    @InjectMocks
    private KafkaConfiguration kafkaConfiguration;
    @Mock
    private TopicName topicName;
    private static final String KAFKA_BROKER_CONNECTION = "kafka:9094";
    private static final String TOPIC_NAME = "topic-name";
    private static final String TOPIC_NAME_CONSUMER = TOPIC_NAME.concat("-communications");
    private static final Pattern TOPIC_NAME_PATTERN = Pattern.compile(".*" + TOPIC_NAME);
}