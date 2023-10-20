package com.pplanello.learning.spring.project.kafka;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Map;

@Component
public class KafkaReactiveConsumer {
    @PostConstruct
    public void startConsume() {
        subscription = kafkaConsumerTemplate.receive()
            .groupBy(kafkaRecord -> kafkaRecord.receiverOffset().topicPartition())
            .flatMap(this::consumerMainEventFlux)
            .subscribe();
    }

    private Flux<Void> consumerMainEventFlux(GroupedFlux<TopicPartition, ReceiverRecord<Integer, byte[]>> partitionFlux) {
        return partitionFlux
            .publishOn(schedulerKafka)
            .concatMap(this::readMessage)
            .doOnNext(this::logMessage)
            .concatMap(this::commitMessage);
    }

    private Mono<KafkaRecord<Map<String, Object>>> readMessage(ReceiverRecord<Integer, byte[]> receiverRecord) {
        return Mono.just(receiverRecord)
            .flatMap(record -> cipherService.decrypt(receiverRecord.value())
                .map(decryptedPayload -> new KafkaRecord<>(receiverRecord, decryptedPayload)));
    }

    private void logMessage(KafkaRecord<Map<String, Object>> kafkaRecord) {
        System.out.println("kafkaRecord = " + kafkaRecord.payload());
    }

    private Mono<Void> commitMessage(KafkaRecord<Map<String, Object>> kafkaRecord) {
        return kafkaRecord.consumerRecord().receiverOffset().commit();
    }

    public void stopConsume() {
        subscription.dispose();
    }

    public KafkaReactiveConsumer(@Qualifier("consumerTopicName") ReactiveKafkaConsumerTemplate<Integer, byte[]> kafkaConsumerTemplate,
                                 CipherService cipherService) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
        this.schedulerKafka = Schedulers.single();
        this.cipherService = cipherService;
    }

    private final ReactiveKafkaConsumerTemplate<Integer, byte[]> kafkaConsumerTemplate;
    private final Scheduler schedulerKafka;
    private final CipherService cipherService;
    private Disposable subscription;
}
