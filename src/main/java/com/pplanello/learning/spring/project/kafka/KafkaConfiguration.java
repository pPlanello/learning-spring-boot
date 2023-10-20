package com.pplanello.learning.spring.project.kafka;

import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean("consumerTopicName")
    public ReactiveKafkaConsumerTemplate<Integer, Map<String, Object>> kafkaConsumerTemplate(
        @Qualifier("kafkaConsumerOptions") ReceiverOptions<Integer, Map<String, Object>> kafkaConsumerOptions
    ) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaConsumerOptions);
    }

    @Bean("kafkaConsumerOptions")
    public ReceiverOptions<Integer, Map<String, Object>> kafkaConsumerOptions() {
        var basicReceiverOptions = ReceiverOptions.<Integer, Map<String, Object>>
            create(getConsumerOptions());

        return basicReceiverOptions
            .subscription(this.topicName.getPattern());
    }

    private Map<String, Object> getConsumerOptions() {
        var consumerProps = new HashMap<String, Object>();

        consumerProps.put(BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerConnection);
        consumerProps.put(GROUP_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(CLIENT_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, MapDeserializer.class);
        consumerProps.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(MAX_POLL_INTERVAL_MS_CONFIG, MINUTES.toMillis(7));
        consumerProps.put(METADATA_MAX_AGE_CONFIG, SECONDS.toMillis(10));
        consumerProps.put(ALLOW_AUTO_CREATE_TOPICS_CONFIG, autoCreateTopic);

        return consumerProps;
    }

    void setKafkaBrokerConnection(String kafkaBrokerConnection) {
        this.kafkaBrokerConnection = kafkaBrokerConnection;
    }

    @Autowired
    private TopicName topicName;
    @Value("${kafka_broker_connection}")
    private String kafkaBrokerConnection;
    @Value("${kafka_auto_create_topic:false}")
    private final Boolean autoCreateTopic = FALSE;
}
