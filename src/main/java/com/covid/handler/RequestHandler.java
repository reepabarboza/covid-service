package com.covid.handler;

import com.covid.constant.ServiceConstants;
import com.covid.service.RapidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Handler class consists of API to fetch the Covid data.
 */
@Named
public class RequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    @Autowired
    private final RapidService rapidService;

    @Inject
    public RequestHandler(final RapidService rapidService) {
        this.rapidService = rapidService;
    }

    public Mono<ServerResponse> healthCheck(final ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ServiceConstants.SUCCESS), String.class);
    }

    /**
     * This method fetches the Covid data from Rapid API, saves in the H2 datastore and returns Page response
     * with Covid data elements and the page details.
     * Its takes optional parameters country, pagenumber and pagesize. If none of the parameters are specified,
     * the covid data is fetched for the entire world. On specifying the country, pagenumber and pagesize,
     * the covid data with the number of records specified in parameter pagesize for the specified country is fetched
     * for the given page number.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> fetchCovidData(ServerRequest request) {
        String country = request.queryParam(ServiceConstants.COUNTRY).orElse(ServiceConstants.EMPTY);

        if (request.queryParam(ServiceConstants.PAGE_NUMBER).isPresent() || request.queryParam(ServiceConstants.PAGE_SIZE).isPresent()) {
            String pageNumber = request.queryParam(ServiceConstants.PAGE_NUMBER).orElse(ServiceConstants.DEFAULT_PAGE_NUMBER);
            String pageSize = request.queryParam(ServiceConstants.PAGE_SIZE).orElse(ServiceConstants.DEFAULT_PAGE_SIZE);
            return rapidService.fetchPagedCovidData(country, Integer.parseInt(pageNumber), Integer.parseInt(pageSize))
                    .flatMap(response -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(response)))
                    .onErrorResume(error -> {
                        LOGGER.error("Error occurred while fetching covid data", error);
                        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching covid data"));
                    });
        } else {
            return rapidService.fetchCovidData(country).flatMap(response -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(response)))
                    .onErrorResume(error -> {
                        LOGGER.error("Error occurred while fetching covid data", error);
                        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching covid data"));
                    });
        }
    }
}
