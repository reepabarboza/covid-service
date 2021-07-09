package com.covid.service;

import com.covid.constant.ServiceConstants;
import com.covid.gateway.RapidApiGateway;
import com.covid.model.CovidData;
import com.covid.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@Service
public class RapidService {
    private RapidApiGateway rapidApiGateway;
    @Autowired
    private CovidRepository covidRepository;

    @Inject
    public RapidService(final RapidApiGateway rapidApiGateway) {
        this.rapidApiGateway = rapidApiGateway;
    }

    public Mono<Page<CovidData>> fetchCovidData(final String country) {
        return rapidApiGateway.fetchCovidData(country)
                .flatMap(response -> {
                    if (response.getData() != null && !CollectionUtils.isEmpty(response.getData().getCovid19Stats())) {
                        covidRepository.saveAll(response.getData().getCovid19Stats());
                    }

                    Pageable pageRequest = PageRequest.of(Integer.parseInt(ServiceConstants.DEFAULT_PAGE_NUMBER),
                            Integer.parseInt(ServiceConstants.DEFAULT_PAGE_SIZE));
                    return Mono.just(covidRepository.findAllByCountry(country, pageRequest));
                });
    }

    public Mono<Page<CovidData>> fetchPagedCovidData(final String country, final Integer pageNumber, final Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return rapidApiGateway.fetchCovidData(country)
                .flatMap(response -> Mono.just(covidRepository.findAllByCountry(country, pageRequest)));
    }
}
