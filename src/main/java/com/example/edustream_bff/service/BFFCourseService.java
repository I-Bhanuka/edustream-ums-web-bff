package com.example.edustream_bff.service;

import com.example.edustream_bff.config.RestClientConfig;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterCourseRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Call Course Microservice to create a new course and return the response to the frontend
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

    // Call Course Microservice to get all courses and return the response to the frontend
    public ApiResponse<PageResponseDTO<Object>> getAllCourses() {

        try {
            log.info("Get all courses method called in BFFCourseService, will call Course MicroService to get all courses");

            String courseBackendUrl =  restClientConfig.getCourseBackendUrl() + restClientConfig.getCourseGetAllEndpoint();

            log.info("Calling Course MicroService get all courses endpoint with URL: {}", courseBackendUrl);

            ResponseEntity<ApiResponse<PageResponseDTO<Object>>> response = restTemplate.exchange(
                    courseBackendUrl,
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<ApiResponse<PageResponseDTO<Object>>>() {}
            );

            log.info("Received response from backend get all courses endpoint: {}", response);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("BFF error when connecting to Course MS for to get all courses: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Failed to connect to backend for retrieval of all courses", e.getStatusCode().value());

        } catch (HttpServerErrorException e) {
            log.error("Server error from Course MS when retrieval of all courses: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Course Service encountered an error." + e.getMessage().formatted(), e.getStatusCode().value());

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Course MS to retrieve all courses: {}", e.getMessage());
            throw new CourseMicroServiceException("Cannot reach Course Service", 503);
        }
    }


    // Call Course Microservice to get a course by ID and return the response to the frontend
    public ApiResponse<Object> getCourseById(BFFCourseRequestDTO bffCourseRequestDTO) {

        try {
            log.info("Get course by ID method called in BFFCourseService, will call Course MicroService to get a course by ID: {}",
                    bffCourseRequestDTO.getCourseId());

            String courseBackendUrl =  restClientConfig.getCourseBackendUrl() + restClientConfig.getCourseGetByIdEndpoint();

            log.info("Calling Course MicroService get course by ID endpoint with URL: {} and payload: {}",
                    courseBackendUrl, bffCourseRequestDTO);

            ResponseEntity<ApiResponse<Object>> response = restTemplate.exchange(
                    courseBackendUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(bffCourseRequestDTO),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            log.info("Received response from backend get course by ID: {}", response);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("BFF error when connecting to Course MS for to get course by id: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Failed to connect to backend for to retrieve course by id", e.getStatusCode().value());

        } catch (HttpServerErrorException e) {
            log.error("Server error from Course MS when retrieval a course: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CourseMicroServiceException("Course Service encountered an error." + e.getMessage().formatted(), e.getStatusCode().value());

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Course MS to retrieve a course: {}", e.getMessage());
            throw new CourseMicroServiceException("Cannot reach Course Service", 503);
        }

    }
}
