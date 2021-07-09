package com.covid.handler;

import com.covid.constant.ServiceConstants;
import com.covid.service.RapidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.restdocs.RestDocumentationContextProvider;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest({RequestHandler.class})
public class RequestHandlerTest {

    private WebTestClient webClient;

    @MockBean
    private RapidService rapidService;

    @BeforeEach
    public void setUp(final ApplicationContext context, final RestDocumentationContextProvider restDocumentation) {
        webClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation))
                .responseTimeout(Duration.ofMillis(50000))
                .build();
    }

    //@Test
    public void testHealthCheck() {
        webClient.get()
                .uri(ServiceConstants.HEALTH_CHECK)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }
}
