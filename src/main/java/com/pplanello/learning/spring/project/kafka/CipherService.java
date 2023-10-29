package com.pplanello.learning.spring.project.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

@Component
public class CipherService {

    public Mono<byte[]> encrypt(Map<String, Object> payload) {
        return Mono.just(payload)
                .map(this::mapParseToString)
                .map(this::encryptPayload);
    }

    public Mono<Map<String, Object>> decrypt(byte[] payloadEncrypted) {
        return Mono.just(payloadEncrypted)
                .map(this::decryptPayload)
                .map(this::stringParseToMap);
    }

    private byte[] encryptPayload(String payloadString) {
        var cipher = getCipherEncryption();
        try {
            return cipher.doFinal(payloadString.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher getCipherEncryption() {
        var cipher = getCipher();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }

    private String decryptPayload(byte[] encryptedPayload) {
        var cipher = getCipherDecryption();
        try {
            return new String(cipher.doFinal(encryptedPayload));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher getCipherDecryption() {
        var cipher = getCipher();
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }

    private Cipher getCipher() {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM_DESCRIPTION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }

    private Map<String, Object> stringParseToMap(String data) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(data, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String mapParseToString(Map<String, Object> data) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CipherService() {
        secretKey = getGeneratedKey();
        ivParameterSpec = getGenerateInitializationVector();
    }

    private SecretKey getGeneratedKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(ALGORITHM_SIZE);
        return keyGenerator.generateKey();
    }

    private IvParameterSpec getGenerateInitializationVector() {
        var iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    SecretKey getSecretKey() {
        return secretKey;
    }

    IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }

    private final SecretKey secretKey;
    private final IvParameterSpec ivParameterSpec;
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_DESCRIPTION = ALGORITHM + "/CBC/NoPadding";
    private static final Integer ALGORITHM_SIZE = 256;
}
