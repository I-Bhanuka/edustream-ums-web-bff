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

    // Student MicroService URLs and Endpoints
    @Value("${services.student.url}")
    private String studentBackendUrl;

    @Value("${services.student.api.test}")
    private String studentTestEndpoint;

    @Value("${services.student.api.create}")
    private String studentCreateEndpoint;

    @Value("${services.student.api.getAll}")
    private String studentGetAllEndpoint;

    @Value("${services.student.api.getById}")
    private String studentGetByIdEndpoint;

    // Course MicroService URLs and Endpoints
    @Value("${services.course.url}")
    private String courseBackendUrl;

    @Value("${services.course.api.create}")
    private String courseCreateEndpoint;

    @Value("${services.course.api.getAll}")
    private String courseGetAllEndpoint;

    @Value("${services.course.api.getById}")
    private String courseGetByIdEndpoint;


    // Create a Singleton RestTemplate bean for making HTTP calls to backend
    @Bean
    public RestTemplate restTemplate() {
        log.info("Initializing RestTemplate with the Student MicroService URL: {}", studentBackendUrl);
        log.info("Initializing RestTemplate with the Course MicroService URL: {}",courseBackendUrl);
        return new RestTemplate();
    }
}
