package com.covid.gateway;

import com.covid.model.RapidResponse;
import com.covid.spring.RapidProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RapidApiGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(RapidApiGateway.class);
    private RapidProperties rapidProperties;
    @Autowired
    WebClient webClient;

    @Inject
    public RapidApiGateway(RapidProperties rapidProperties) {
        this.rapidProperties = rapidProperties;
    }

    public Mono<RapidResponse> fetchCovidData(String countryName) {
        return webClient.get().uri(rapidProperties.getUrl() + countryName)
                .headers(headers -> addHeaders(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RapidResponse.class)
                .flatMap(rapidResponse -> {
                    return Mono.just(rapidResponse);
                })
                .onErrorResume(error -> {
                    LOGGER.error("Error occurred while calling Rapid Api for country: {}, error : {}", countryName, error);
                    return Mono.error(error);
                });
    }

    private void addHeaders(final HttpHeaders headers) {
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("X-RapidAPI-Key", rapidProperties.getKey());
        headers.set("X-RapidAPI-Host", rapidProperties.getHost());
    }
}
