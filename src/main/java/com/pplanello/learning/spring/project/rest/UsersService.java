package com.pplanello.learning.spring.project.rest;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface UsersService {

    Mono<Map<String, Object>> getAllUsers();
}
