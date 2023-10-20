package com.pplanello.learning.spring.project.kafka;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CipherService {

    public Mono<byte[]> encrypt(Map<String, Object> payload) {
        return null;
    }

    public Mono<Map<String, Object>> decrypt(byte[] encrypted) {
        return null;
    }
}
