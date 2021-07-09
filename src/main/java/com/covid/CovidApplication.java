package com.covid;

import com.covid.spring.RapidProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties({RapidProperties.class})
public class CovidApplication {
	public static void main(String[] args) {
		SpringApplication.run(CovidApplication.class, args);
	}

}
