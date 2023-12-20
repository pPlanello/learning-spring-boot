package com.pplanello.learning.spring.project.rest;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class UsersService {

    public Mono<List<Map<String, Object>>> getAllUsers() {
        return null;
    }
}
