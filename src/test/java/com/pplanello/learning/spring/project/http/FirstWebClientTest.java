package com.pplanello.learning.spring.project.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@ExtendWith(MockitoExtension.class)
class FirstWebClientTest {

    @Test
    void it_should_return_not_null() {
        setupResponseClient(PATH_POST_TEST, "POST", 200, RESPONSE_OK);

        assertThat(firstWebClient.sendPost(ALGORITHM, LABEL, request))
                .as("It should return not null")
                .isNotNull();
    }


    @Test
    void it_should_return_expected_response() {
        setupResponseClient(PATH_POST_TEST, "POST", 200, RESPONSE_OK);

        StepVerifier.create(firstWebClient.sendPost(ALGORITHM, LABEL, request))
                .as("It should return expected response")
                .expectNext(RESPONSE_OK)
                .verifyComplete();
    }

    @BeforeEach
    void startServer() {
        server = ClientAndServer.startClientAndServer(FIRST_AVAILABLE_PORT);
        var port = server.getPort();
        var webclient = new WebClientConfiguration().firstWebClient("http://127.0.0.1:" + port, WebClient.builder());
        firstWebClient = new FirstWebClient(webclient);
    }

    @AfterEach
    void stopServer() {
        server.stop();
    }

    private void setupResponseClient(String path, String typeMethod, Integer statusCode, Map<String, Object> body) {
        server.when(request()
                        .withMethod(typeMethod)
                        .withPath(path)
                        .withBody(json(request, MatchType.ONLY_MATCHING_FIELDS)))
                .respond(response()
                        .withStatusCode(statusCode)
                        .withBody(mapToJsonString(body)));
    }

    private String mapToJsonString(Map<String, Object> body) {
        try {
            return new ObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private FirstWebClient firstWebClient;
    @Mock
    private Map<String, Object> request;

    // using mockserver-netty to Testing
    private static ClientAndServer server;

    private static final Integer FIRST_AVAILABLE_PORT = 0;
    private static final String ALGORITHM = "algorithm";
    private static final String LABEL = "label";
    private static final String PATH_POST_TEST = "/api/" + ALGORITHM + "/" + LABEL + "/test";
    private static final Map<String, Object> RESPONSE_OK = Map.of("response", "ok");
}