package com.pplanello.learning.spring.project.rest;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface UsersService {

    Mono<List<Map<String, Object>>> getAllUsers();
}
