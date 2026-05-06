package com.example.edustream_bff.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Getter
@Configuration
public class RestClientConfig {

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${backend.api.students}")
    private String studentEndpoint;

    @Value("${backend.api.courses}")
    private String courseEndpoint;

    @Value("${backend.api.auth}")
    private String authEndpoint;

    // Create a Singleton RestTemplate bean for making HTTP calls to backend
    @Bean
    public RestTemplate restTemplate() {
        log.info("Initializing RestTemplate with backend URL: {}", backendUrl);
        return new RestTemplate();
    }
}
