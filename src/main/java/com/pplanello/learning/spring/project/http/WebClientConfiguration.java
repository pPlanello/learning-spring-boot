package com.pplanello.learning.spring.project.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;

@Configuration
public class WebClientConfiguration {

    @Bean("first-web-client")
    public WebClient firstWebClient(@Value("${FIRST_WEB_CLIENT}") String host,
                                    @Autowired WebClient.Builder webClientBuilder) {
        var webClientFactory = new WebClientFactory(webClientBuilder,
                host,
                "firstClient",
                HttpProtocol.HTTP11);
        return webClientFactory.create();
    }

    @Bean("second-web-client")
    public WebClient secondWebClient(@Value("${FIRST_WEB_CLIENT:localhost}") String host,
                                     @Autowired WebClient.Builder webClientBuilder) {
        var webClientFactory = new WebClientFactory(webClientBuilder,
                host,
                "secondClient",
                HttpProtocol.H2C);
        return webClientFactory.create();
    }
}
