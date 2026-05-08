package com.example.edustream_bff.service;

import com.example.edustream_bff.config.RestClientConfig;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterCourseRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterCourseResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.exception.CourseMicroServiceException;
import com.example.edustream_bff.exception.StudentMicroServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class BFFCourseService {

    // To make HTTP calls to the backend. This what we created in RestClientConfig as a bean, we can inject it here.
    // RestTemplate acts as our client and makes HTT requests to the backend and gets the responses.
    private final RestTemplate restTemplate;

    // To access the helper methods like getBackendUrl()
    // RestClientConfig has all of our URLs as its properties
    private final RestClientConfig restClientConfig;

    // Call Student Microservice to create a new student and return the response to the frontend
    public ApiResponse<BFFRegisterCourseResponseDTO> registerCourse(BFFRegisterCourseRequestDTO registerCourseRequestDTO) {

        try {
            log.info("Create course method called in BFFCourseService, will call course MicroService to create a new course");

            String courseBackendUrl =  restClientConfig.getCourseBackendUrl() + restClientConfig.getCourseCreateEndpoint();

            log.info("Calling Course MicroService create course endpoint with URL: {}", courseBackendUrl);

            ResponseEntity<ApiResponse<BFFRegisterCourseResponseDTO>> response = restTemplate.exchange(
                    courseBackendUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(registerCourseRequestDTO),
                    new ParameterizedTypeReference<ApiResponse<BFFRegisterCourseResponseDTO>>() {}
            );


            log.info("Received response from backend create course endpoint: {}", response);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("BFF error when connecting to Course MS for course registration: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Failed to connect to backend for create course", e.getStatusCode().value());

        } catch (HttpServerErrorException e) {
            log.error("Server error from Course MS when course registration: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Course Service encountered an error." + e.getMessage().formatted(), e.getStatusCode().value());

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Course MS to register a course: {}", e.getMessage());
            throw new CourseMicroServiceException("Cannot reach Course Service", 503);
        }
    }
}
