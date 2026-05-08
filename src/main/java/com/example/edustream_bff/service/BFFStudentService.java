package com.example.edustream_bff.service;

import com.example.edustream_bff.config.RestClientConfig;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
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

import java.lang.reflect.Array;

@Slf4j
@RequiredArgsConstructor
@Service
public class BFFStudentService {

    // To make HTTP calls to the backend. This what we created in RestClientConfig as a bean, we can inject it here.
    // RestTemplate acts as our client and makes HTT requests to the backend and gets the responses.
    private final RestTemplate restTemplate;

    // To access the helper methods like getBackendUrl(), getStudentsEndpoint()
    // RestClientConfig has all of our URLs as its properties
    private final RestClientConfig restClientConfig;

//    /***
//     * Fetch all students from backend and transform to WebStudentDTO
//     */
//
//    public List<WebStudentDTO> getAllStudentsForWeb(String authorizationHeader) {
//
//        try {
//            log.info("Fetching all students from backend");
//
//            // 1. Prepare headers with JWT token
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", authorizationHeader);
//            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//
//            // 2. Call backend endpoint
//            String backendUrl = restClientConfig.getBackendUrl() + restClientConfig.getStudentEndpoint();
//
//            log.info("Calling backend with URL: {} to fetch students", backendUrl);
//
//            BackendStudentResponseDTO [] backendStudents = restTemplate.exchange(
//                    backendUrl,
//                    HttpMethod.GET,
//                    requestEntity,
//                    BackendStudentResponseDTO[].class
//            ).getBody();
//
//
//            // 3. Transform backend response to Web DTOs
//            List<WebStudentDTO> webStudents = new ArrayList<>();
//
//            if (backendStudents != null) {
//                for (BackendStudentResponseDTO backendStudent : backendStudents) {
//                    WebStudentDTO webStudent = WebStudentDTO.builder()
//                            .firstName(backendStudent.getFirstName())
//                            .lastName(backendStudent.getLastName())
//                            .email(backendStudent.getEmail())
//                            .courseName(backendStudent.getCourse() == null ? "N/A" : backendStudent.getCourse().getCourseName())
//                            .build();
//                    webStudents.add(webStudent);
//                }
//            }
//
//            log.info("Successfully transformed {} students for web", webStudents.size());
//            return webStudents;
//
//        } catch (Exception e) {
//            log.error("Error fetching students from backend", e);
//            throw new RuntimeException("Failed to fetch students from backend", e);
//        }
//
//    }
//
//    /**
//     * Register a new student by sending the data to backend
//     */
//
//    public BackendResponseDTO registerNewStudent(String authorizationHeader, WebRegisterStudentDTO webRegisterStudentDTO) {
//
//        try {
//
//            // 1. Prepare headers with JWT token
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", authorizationHeader); // Set the header
//            headers.setContentType(MediaType.APPLICATION_JSON); // Set the content type to JSON
//
//            // Include the body as well
//            HttpEntity<WebRegisterStudentDTO> requestEntity = new HttpEntity<>(webRegisterStudentDTO, headers);
//
//            // 2. Call backend endpoint
//            String backendUrl = restClientConfig.getBackendUrl() + restClientConfig.getStudentEndpoint();
//
//            log.info("Calling backend to register new student with URL: {} and payload: {}", backendUrl, webRegisterStudentDTO);
//
//            BackendResponseDTO response = restTemplate.exchange(
//                    backendUrl,
//                    HttpMethod.POST,
//                    requestEntity,
//                    BackendResponseDTO.class
//            ).getBody();
//
//            log.info("Successfully registered student to backend with response: {}", response);
//            return response;
//
//        } catch (Exception e) {
//            log.error("Error registering student to backend", e);
//            throw new RuntimeException("Failed to register student to backend", e);
//        }
//
//
//    }

    public String testEndpoint() {

        try {
            log.info("Testing backend connectivity from StudentService");

            String studentBackendUrl =  restClientConfig.getStudentBackendUrl() + restClientConfig.getStudentTestEndpoint();

            log.info("Calling backend test endpoint with URL: {}", studentBackendUrl);

            String response = restTemplate.getForObject(studentBackendUrl, String.class);

            log.info("Received response from backend test endpoint: {}", response);

            return response;

        } catch (Exception e) {
            log.error("Error testing backend connectivity", e);
            throw new RuntimeException("Failed to connect to backend", e);
        }
    }


    // Call Student Microservice to create a new student and return the response to the frontend
    public ApiResponse<BFFRegisterStudentResponseDTO> registerStudent(BFFRegisterStudentRequestDTO registerStudentRequestDTO) {

        try {
            log.info("Create student method called in BFFStudentService, will call Student MicroService to create a new student");

            String studentBackendUrl =  restClientConfig.getStudentBackendUrl() + restClientConfig.getStudentCreateEndpoint();

            log.info("Calling Student MicroService create student endpoint with URL: {}", studentBackendUrl);

            ResponseEntity<ApiResponse<BFFRegisterStudentResponseDTO>> response = restTemplate.exchange(
                    studentBackendUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(registerStudentRequestDTO),
                    new ParameterizedTypeReference<ApiResponse<BFFRegisterStudentResponseDTO>>() {}
            );


            log.info("Received response from backend create student endpoint: {}", response);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("BFF error when connecting to Student MS: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new StudentMicroServiceException("Failed to connect to backend for create student", e.getStatusCode().value());

        } catch (HttpServerErrorException e) {
            log.error("Server error from Student MS: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new StudentMicroServiceException("Student Service encountered an error." + e.getMessage().formatted(), e.getStatusCode().value());

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }


    // Call Student Microservice to get all students and return the response to the frontend
    public ApiResponse<PageResponseDTO<Object>> getAllStudents(BFFStudentRequestDTO studentRequestDTO) {

            try {
                log.info("Get all students method called in BFFStudentService, will call Student MicroService to get all students");

                String studentBackendUrl =  restClientConfig.getStudentBackendUrl() + restClientConfig.getStudentGetAllEndpoint();

                log.info("Calling Student MicroService get all students endpoint with URL: {}", studentBackendUrl);

                ResponseEntity<ApiResponse<PageResponseDTO<Object>>> response = restTemplate.exchange(
                        studentBackendUrl,
                        HttpMethod.POST,
                        new HttpEntity<>(studentRequestDTO),
                        new ParameterizedTypeReference<ApiResponse<PageResponseDTO<Object>>>() {}
                );

                log.info("Received response from backend get all students endpoint: {}", response);

                return response.getBody();

            } catch (Exception e) {
                log.error("Error getting all students from backend", e);
                throw new RuntimeException("Failed to get students from backend", e);
            }
    }



}
