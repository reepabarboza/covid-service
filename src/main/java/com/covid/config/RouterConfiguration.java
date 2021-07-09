package com.covid.config;

import com.covid.constant.ServiceConstants;
import com.covid.handler.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.inject.Inject;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@ComponentScan({"com.covid.*"})
public class RouterConfiguration {

    private final RequestHandler requestHandler;

    @Inject
    public RouterConfiguration(final RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return route(GET(ServiceConstants.HEALTH_CHECK).and(accept(APPLICATION_JSON)), requestHandler::healthCheck)
                .andRoute(GET(ServiceConstants.FETCH_COVID_DATA_URI).and(accept(APPLICATION_JSON)), requestHandler::fetchCovidData);
    }
}
