package com.pplanello.learning.spring.project.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> getAllUsers() {
        return usersService.getAllUsers()
            .map(ResponseEntity::ok);
    }

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    private final UsersService usersService;
}
