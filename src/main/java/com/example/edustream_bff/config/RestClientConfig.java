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

    /***
     * This is our own factory for creating RestTemplate instances and holding URL properties.
     * Spring will come and scan this file and then create a RestTemplate bean that we can inject anywhere in our code.
     */

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
