package com.pplanello.learning.spring.project.kafka;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CipherServiceTest {

    @Test
    void it_should_encrypt_and_decrypt_same_message() {
        var encrypt = cipherService.encrypt(MESSAGE);

        System.out.println("encrypt = " + encrypt.block());

        StepVerifier.create(encrypt.flatMap(cipherService::decrypt))
                .assertNext(result -> assertEquals(MESSAGE, result))
                .verifyComplete();
    }

    private final CipherService cipherService = new CipherService();

    private static final Map<String, Object> MESSAGE = Map.of("test", "test");
}