package com.example.edustream_bff.service;

import com.example.edustream_bff.client.StudentServiceClient;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.exception.StudentMicroServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class BFFStudentService {

    private final StudentServiceClient studentServiceClient;


    // Call Student Microservice to create a new student and return the response to the frontend
    public ApiResponse<BFFRegisterStudentResponseDTO> registerStudent(BFFRegisterStudentRequestDTO registerStudentRequestDTO) {

        try {
            log.info("Create student method called in BFFStudentService, will call Student MicroService to create a new student");

            ApiResponse<BFFRegisterStudentResponseDTO> response = studentServiceClient.registerStudent(registerStudentRequestDTO);

            log.info("Received response from backend create student endpoint: {}", response);

            return response;

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for student registration: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when student registration: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to register a student: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }


    // Call Student Microservice to get all students and return the response to the frontend
    public ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> getAllStudentsWithLimitedDetails() {

            try {
                log.info("Get all students method called in BFFStudentService, will call Student MicroService to get all students");

                ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> response = studentServiceClient.
                        getAllStudentsWithLimitedDetails();


                log.info("Received response from backend get all students endpoint: {}", response);

                return response;

                // The MS thinks that the BFF made a mistake - 4xx errors
            } catch (HttpClientErrorException e) {
                String downStreamMessage = e.getResponseBodyAsString();
                log.error("BFF error when connecting to Student MS for to get all students: {} - {}", e.getStatusCode().value(),
                        downStreamMessage);
                throw new StudentMicroServiceException("Student Service rejected the request",
                        e.getStatusCode().value(),
                        downStreamMessage);

                // The MS has an issue processing the request - 5xx errors
            } catch (HttpServerErrorException e) {
                String downStreamMessage = e.getResponseBodyAsString();
                log.error("Server error from Student MS when retrieval of all students: {} - {}", e.getStatusCode().value(),
                        downStreamMessage);
                throw new StudentMicroServiceException("Student Service encountered an error.", e.getStatusCode().value(),
                        downStreamMessage);

            } catch (ResourceAccessException e) {
                log.error("Network error reaching Student MS to retrieve all students: {}", e.getMessage());
                throw new StudentMicroServiceException("Cannot reach Student Service", 503);
            }
    }

    // Call Student Microservice to get a student by ID and return the response to the frontend
    public BFFLimitedStudentResponseDTO getStudentById(BFFStudentRequestDTO bffStudentRequestDTO) {

        try {
            log.info("Get student by ID method called in BFFStudentService, will call Student MicroService to get a student by ID: {}",
                    bffStudentRequestDTO.getStudentId());

            ApiResponse<BackendStudentResponseDTO> response = studentServiceClient.getStudentById(bffStudentRequestDTO);

            log.info("Received response from backend get student by ID: {}", response);

            // Extracting only the needed details to return to the frontend, we don't want to return the entire backend response which may contain more details than needed
            return BFFLimitedStudentResponseDTO.builder()
                    .studentId(response.getData().getStudentId())
                    .firstName(response.getData().getFirstName())
                    .lastName(response.getData().getLastName())
                    .email(response.getData().getEmail())
                    .dob(response.getData().getDob())
                    .enrollmentDate(response.getData().getEnrollmentDate())
                    .studentStatus(response.getData().getStudentStatus())
                    .courseUUID(response.getData().getCourseUUID() != null ? response.getData().getCourseUUID() : null)
                    .build();

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to get student by id: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when retrieval a student: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to retrieve a student: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }

    }



}
