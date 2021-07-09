package com.covid.model;

import java.util.List;

@lombok.Data
public class Data {
    private String lastChecked;
    private List<CovidData> covid19Stats;
}
