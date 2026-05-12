package com.example.edustream_bff.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Getter
@Configuration
public class RestClientConfig {

    /***
     * This is our own factory for creating RestTemplate instances and holding URL properties.
     * Spring will come and scan this file and then create a RestTemplate bean that we can inject anywhere in our code.
     */

    // Student MicroService Base URL
    @Value("${services.student.url}")
    private String studentBackendUrl;

    // Course MicroService Base URL
    @Value("${services.course.url}")
    private String courseBackendUrl;

    // Saga Base URL
    @Value("${services.saga.url}")
    private String sagaUrl;


    // Create a Separate RestClient for Student MicroService
    @Bean
    @Qualifier("studentRestClient")
    public RestClient studentRestClient() {
        return RestClient.builder()
                .baseUrl(studentBackendUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Set the default return type to JSON
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) // Set the default accept header to JSON
                .build();

    }

    @Bean
    @Qualifier("courseRestClient")
    public RestClient courseRestClient() {
        return RestClient.builder()
                .baseUrl(courseBackendUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @Qualifier("sagaRestClient")
    public RestClient sagaRestClient() {
        return RestClient.builder()
                .baseUrl(sagaUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
