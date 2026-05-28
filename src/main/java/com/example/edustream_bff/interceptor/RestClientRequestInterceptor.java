package com.example.edustream_bff.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Configuration
@Slf4j
public class RestClientRequestInterceptor implements ClientHttpRequestInterceptor {

    @NullMarked
    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        // Get the current incoming request from thread-local storage
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        log.info("======> Intercepting outgoing RestClient request to URL: {}", request.getURI());

        if (attributes != null) {
            // Read the Authorization header from it
            String authHeader = attributes.getRequest().getHeader("Authorization");
            if (authHeader != null) {
                // Attach it to the outgoing RestClient request
                log.info("Forwarding Authorization header to microservice: {}", request.getURI());
                request.getHeaders().set("Authorization", authHeader);
            }
        }

        return execution.execute(request, body);
    }
}