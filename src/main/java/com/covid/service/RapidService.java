package com.covid.service;

import com.covid.constant.ServiceConstants;
import com.covid.gateway.RapidApiGateway;
import com.covid.model.CovidData;
import com.covid.repository.CovidRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RapidService.class);

    private RapidApiGateway rapidApiGateway;
    @Autowired
    private CovidRepository covidRepository;

    @Inject
    public RapidService(final RapidApiGateway rapidApiGateway) {
        this.rapidApiGateway = rapidApiGateway;
    }


    /**
     * This method fetches the Covid data from Rapid API for the given country, saves in the H2 datastore
     * and returns Page response with Covid data elements and the page details. If country is empty then
     * covid data for the world is returned
     *
     * @param country
     * @return
     */
    public Mono<Page<CovidData>> fetchCovidData(final String country) {
        return rapidApiGateway.fetchCovidData(country)
                .flatMap(response -> {
                    if (response.getData() != null && !CollectionUtils.isEmpty(response.getData().getCovid19Stats())) {
                        LOGGER.info("Save Covid data : {}",  response.getData().getCovid19Stats());
                        covidRepository.saveAll(response.getData().getCovid19Stats());
                    }

                    Pageable pageRequest = PageRequest.of(Integer.parseInt(ServiceConstants.DEFAULT_PAGE_NUMBER),
                            Integer.parseInt(ServiceConstants.DEFAULT_PAGE_SIZE));

                    return Mono.just(StringUtils.isNotBlank(country)
                            ? covidRepository.findAllByCountry(country, pageRequest) : covidRepository.findAll(pageRequest));
                });
    }

    /**
     * This method fetches the Covid data from H2 database for the given country, pagenumber and pagesize
     * and returns a Page response.
     *
     * @param country
     * @return
     */
    public Mono<Page<CovidData>> fetchPagedCovidData(final String country, final Integer pageNumber, final Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        LOGGER.info("Fetch Covid data for country: {}",  country);
        return Mono.just(StringUtils.isNotBlank(country)
                ? covidRepository.findAllByCountry(country, pageRequest) : covidRepository.findAll(pageRequest));
    }
}
