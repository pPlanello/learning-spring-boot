package com.pplanello.learning.spring.project.http;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class FirstWebClient {

    public Mono<Map<String, Object>> sendPost(String keyAlgorithm,
                                              String keyLabel,
                                              Map<String, Object> requestBody) {
        var uriTemplate = "/api/" + keyAlgorithm + "/{keyLabel}/test";
        var uriParams = List.of(keyLabel);

        return webClient
                .post()
                .uri(uriTemplate, uriParams.toArray())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::httpErrorHandler)
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    private Mono<? extends Throwable> httpErrorHandler(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(String.class)
                .defaultIfEmpty("")
                .flatMap(body -> Mono.error(
                        new FirstClientException(
                                String.valueOf(clientResponse.statusCode().value()), body)));
    }

    public FirstWebClient(@Qualifier("first-web-client") WebClient webClient) {
        this.webClient = webClient;
    }

    private final WebClient webClient;
}
