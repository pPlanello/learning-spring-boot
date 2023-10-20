package com.pplanello.learning.spring.project.kafka;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
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
    public ReactiveKafkaConsumerTemplate<Integer, byte[]> kafkaConsumerTemplate(
        @Qualifier("kafkaConsumerOptions") ReceiverOptions<Integer, byte[]> kafkaConsumerOptions
    ) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaConsumerOptions);
    }

    @Bean("kafkaConsumerOptions")
    public ReceiverOptions<Integer, byte[]> kafkaConsumerOptions() {
        var basicReceiverOptions = ReceiverOptions.<Integer, byte[]>
            create(getConsumerOptions())
                .withKeyDeserializer(new IntegerDeserializer())
                .withValueDeserializer(new ByteArrayDeserializer());

        return basicReceiverOptions
            .subscription(this.topicName.getPattern());
    }

    private Map<String, Object> getConsumerOptions() {
        var consumerProps = new HashMap<String, Object>();

        consumerProps.put(BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerConnection);
        consumerProps.put(GROUP_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(CLIENT_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(MAX_POLL_INTERVAL_MS_CONFIG, (int) MINUTES.toMillis(7));
        consumerProps.put(METADATA_MAX_AGE_CONFIG, (int) SECONDS.toMillis(10));
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
