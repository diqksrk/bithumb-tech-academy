package com.codestates.homework.handler;

import com.codestates.homework.model.ResponseHello;
import com.codestates.homework.webclient.InfoServiceWebClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {
    private final InfoServiceWebClient infoServiceWebClient;

    public Handler(InfoServiceWebClient infoServiceWebClient) {
        this.infoServiceWebClient = infoServiceWebClient;
    }

    public Mono<ServerResponse> helloHandle(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse("");

        Mono<ResponseHello> responseHelloMono = infoServiceWebClient.requestJob(name)
                                                                    .map(mapJob -> {
                                                                        return new ResponseHello(name, mapJob.get("job").toString());
                                                                    });

        return ServerResponse
                .ok()
                .body(responseHelloMono, ResponseHello.class);
    }
}
