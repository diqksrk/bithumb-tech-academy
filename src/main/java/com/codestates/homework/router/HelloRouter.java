package com.codestates.homework.router;

import com.codestates.homework.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> router(Handler handler) {
        return RouterFunctions.route()
                .GET("/hello", handler::helloHandle)
                .build();
    }
}
