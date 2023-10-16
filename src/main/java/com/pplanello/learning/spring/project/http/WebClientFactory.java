package com.pplanello.learning.spring.project.http;


import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Component
public class WebClientFactory {

    public WebClient create() {
        var httpClient = getHttpClient();

        return getWebClient(httpClient, baseUrl);
    }

    private HttpClient getHttpClient() {
        var connectionProvider = ConnectionProvider.builder(clientName + "ConnectionPool")
                .maxConnections(1000)
                .pendingAcquireMaxCount(2000)
                .build();

        return HttpClient.create(connectionProvider)
                .protocol(httpProtocol)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .responseTimeout(Duration.ofMillis(400))
                .resolver(spec -> spec.roundRobinSelection(true))
                .wiretap(true);
    }

    private WebClient getWebClient(HttpClient httpClient, String url) {
        return webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(url)
                .build();
    }

    public WebClientFactory(
            WebClient.Builder webClientBuilder,
            String baseUrl,
            String clientName,
            HttpProtocol httpProtocol
    ) {
        this.webClientBuilder = webClientBuilder;
        this.baseUrl = baseUrl;
        this.clientName = clientName;
        this.httpProtocol = httpProtocol;
    }

    private final WebClient.Builder webClientBuilder;
    private final String baseUrl;
    private final String clientName;
    private final HttpProtocol httpProtocol;

}
