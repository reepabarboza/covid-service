package com.covid.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rapid.api")
@Data
public class RapidProperties {
    private String url;
    private String key;
    private String host;
}
