package com.example.edustream_bff.service;

import com.example.edustream_bff.config.RestClientConfig;
import com.example.edustream_bff.dto.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.WebStudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {

    // To make HTTP calls to the backend. This what we created in RestClientConfig as a bean, we can inject it here.
    private final RestTemplate restTemplate;

    // To access the helper methods like getBackendUrl(), getStudentsEndpoint()
    private final RestClientConfig restClientConfig;

    /***
     * Fetch all students from backend and transform to WebStudentDTO
     */

    public List<WebStudentDTO> getAllStudentsForWeb(String authorizationHeader) {

        try {
            log.info("Fetching all students from backend");

            // 1. Prepare headers with JWT token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // 2. Call backend endpoint
            String backendUrl = restClientConfig.getBackendUrl() + restClientConfig.getStudentEndpoint();

            BackendStudentResponseDTO [] backendStudents = restTemplate.exchange(
                    backendUrl,
                    HttpMethod.GET,
                    requestEntity,
                    BackendStudentResponseDTO[].class
            ).getBody();


            // 3. Transform backend response to Web DTOs
            List<WebStudentDTO> webStudents = new ArrayList<>();

            if (backendStudents != null) {
                for (BackendStudentResponseDTO backendStudent : backendStudents) {
                    WebStudentDTO webStudent = WebStudentDTO.builder()
                            .firstName(backendStudent.getFirstName())
                            .lastName(backendStudent.getLastName())
                            .email(backendStudent.getEmail())
                            .courseName(backendStudent.getCourse() == null ? "N/A" : backendStudent.getCourse().getCourseName())
                            .build();
                    webStudents.add(webStudent);
                }
            }

            log.info("Successfully transformed {} students for web", webStudents.size());
            return webStudents;

        } catch (Exception e) {
            log.error("Error fetching students from backend", e);
            throw new RuntimeException("Failed to fetch students from backend", e);
        }

    }



}
