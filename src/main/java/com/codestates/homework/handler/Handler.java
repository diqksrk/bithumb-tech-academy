package com.codestates.homework.handler;

import com.codestates.homework.model.ResponseHello;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    public Mono<ServerResponse> helloHandle(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse("");

        return ServerResponse
                .ok()
                .bodyValue(new ResponseHello(name));
    }
}
