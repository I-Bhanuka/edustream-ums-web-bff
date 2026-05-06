package com.example.edustream_bff.controller;

import com.example.edustream_bff.config.RestClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bff")
public class GreetingController {

    private final RestClientConfig restClientConfig;

    @GetMapping("/health")
    public String health(){
        log.info("Health check called");
        log.debug("Backend Students URL: {}", restClientConfig.getStudentEndpoint());
        return "BFF is running! Backend URL: " + restClientConfig.getStudentEndpoint();
    }
}
