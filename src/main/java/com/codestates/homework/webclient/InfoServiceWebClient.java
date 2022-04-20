package com.codestates.homework.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class InfoServiceWebClient {
    private WebClient webClient;

    public InfoServiceWebClient() {
        this.webClient = WebClient.create("http://localhost:8081");
    }

    public Mono<Map> requestJob(String name) {
        Mono<Map> responseJob = this.webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/job")
                                .queryParam("name", name)
                                .build()
                )
                .retrieve()
                .bodyToMono(Map.class);

        return responseJob;
    }
}
