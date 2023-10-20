package com.pplanello.learning.spring.project.kafka;

import reactor.kafka.receiver.ReceiverRecord;

public record KafkaRecord<T>(ReceiverRecord<Integer, byte[]> consumerRecord, T payload) {

    public <T> KafkaRecord<T> withPayload(T newPayload) {
        return new KafkaRecord<>(consumerRecord, newPayload);
    }
}
