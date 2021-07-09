package com.covid.repository;

import com.covid.model.CovidData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CovidRepository extends PagingAndSortingRepository<CovidData, Integer> {
    Page<CovidData> findAllByCountry(String country, Pageable pageable);
}
