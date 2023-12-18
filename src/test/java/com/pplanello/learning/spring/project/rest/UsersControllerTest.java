package com.pplanello.learning.spring.project.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Test
    void it_should_return_not_null() {
        when(usersService.getAllUsers()).thenReturn(Mono.just(LIST_USERS));
        
        assertThat(usersController.getAllUsers())
            .as("It should return not null")
            .isNotNull();
    }

    @Test
    void it_should_call_users_service_to_get_all_users() {
        when(usersService.getAllUsers()).thenReturn(Mono.just(LIST_USERS));

        StepVerifier.create(usersController.getAllUsers())
            .as("It should call users service to get all users")
            .assertNext(result -> verify(usersService, only()
                .description("It should call users service to get all users"))
                .getAllUsers())
            .verifyComplete();
    }

    @Test
    void it_should_return_expected_list_of_users_service_to_get_all_users() {
        when(usersService.getAllUsers()).thenReturn(Mono.just(LIST_USERS));

        StepVerifier.create(usersController.getAllUsers())
            .as("It should return expected list of users service to get all users")
            .expectNext(ResponseEntity.ok(LIST_USERS))
            .verifyComplete();
    }

    @Test
    void it_should_return_error_when_users_service_get_all_users_throw_error() {
        when(usersService.getAllUsers()).thenReturn(Mono.error(new Throwable()));

        StepVerifier.create(usersController.getAllUsers())
            .as("It should call users service to get all users")
            .verifyError();
    }

    @BeforeEach
    void setup() {
        usersController = new UsersController(usersService);
    }

    private UsersController usersController;
    @Mock
    private UsersService usersService;

    private static final Map<String, Object> LIST_USERS = Map.of();
}