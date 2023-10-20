package com.pplanello.learning.spring.project.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.mockito.Mockito.*;

@EmbeddedKafka(partitions = 40, brokerProperties = {
    "KAFKA_LISTENERS = PLAINTEXT://localhost:9093",
    "TOPIC_AUTO_CREATE: true"
}, ports = 9093, topics = "topic-name")
@SpringBootTest(properties = {
    "kafka_broker_connection = localhost:9093",
    "kafka_auto_create_topic = true"
}, classes = {
    KafkaConfiguration.class,
    KafkaReactiveConsumer.class,
    KafkaReactiveConsumerIntegratedTest.class,
    TopicName.class
})
@EnableAutoConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KafkaReactiveConsumerIntegratedTest {

    @Test
    void it_should_decrypt_message_when_received_message() {
        sendMessageToTopic("topic-name", payloadEncrypt, payloadDecrypt);
        verify(cipherService, after(3_000)
            .description("It should call cipher service to decrypt message"))
            .decrypt(payloadEncrypt);
    }

    @BeforeEach
    void setupProducer() {
        var producerFactory = new DefaultKafkaProducerFactory<>(KAFKA_CONFIGURATION, new IntegerSerializer(), new ByteArraySerializer());
        producer = producerFactory.createProducer();
    }

    private void sendMessageToTopic(String topicName, byte[] payloadEncrypt, Map<String, Object> payloadDecrypt) {
        var producerRecord = new ProducerRecord<Integer, byte[]>(topicName, payloadEncrypt);
        producer.send(producerRecord);
        when(cipherService.encrypt(payloadDecrypt)).thenReturn(Mono.just(payloadEncrypt));
        when(cipherService.decrypt(payloadEncrypt)).thenReturn(Mono.just(payloadDecrypt));
    }

    @Autowired
    private KafkaReactiveConsumer kafkaReactiveConsumer;
    @MockBean
    private CipherService cipherService;

    private Producer<Integer, byte[]> producer;
    private static final Map<String, Object> KAFKA_CONFIGURATION = Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
    private Map<String, Object> payloadDecrypt = Map.of("Prueba", "PRueba");
    private byte[] payloadEncrypt = "Prueba".getBytes();
}