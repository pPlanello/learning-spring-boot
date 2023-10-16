package com.pplanello.learning.spring.project.kafka;

import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ReceiverOptions<Integer, Map<String, Object>> kafkaReceiverOptions() {
        var basicReceiverOptions = ReceiverOptions.<Integer, Map<String, Object>>
                create(getConsumerOptions());

        return basicReceiverOptions
                .subscription(this.topicName.getPattern());
    }

    private Map<String, Object> getConsumerOptions() {
        var consumerProps = new HashMap<String, Object>();

        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerConnection);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, topicName.getName().concat("-communications"));
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MapDeserializer.class);
        
        return consumerProps;
    }

    void setKafkaBrokerConnection(String kafkaBrokerConnection) {
        this.kafkaBrokerConnection = kafkaBrokerConnection;
    }

    @Autowired
    private TopicName topicName;
    @Value("${kafka_broker_connection}")
    private String kafkaBrokerConnection;
}
