package com.example.edustream_bff.service;

import com.example.edustream_bff.client.CourseServiceClient;
import com.example.edustream_bff.client.SagaServiceClient;
import com.example.edustream_bff.client.StudentServiceClient;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.*;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.exception.CourseMicroServiceException;
import com.example.edustream_bff.exception.StudentMicroServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class BFFStudentService {

    private final StudentServiceClient studentServiceClient;

    private final CourseServiceClient courseServiceClient;

    private final SagaServiceClient sagaServiceClient;

    // Call Student Microservice to create a new student and return the response to the frontend
    public ApiResponse<BFFRegisterStudentResponseDTO> registerStudent(BFFRegisterStudentRequestDTO registerStudentRequestDTO) {

        log.info("Create student method called in BFFStudentService, will call Student MicroService to create a new student");

        ApiResponse<BFFRegisterStudentResponseDTO> response = studentServiceClient.registerStudent(registerStudentRequestDTO);

        log.info("Received response from backend create student endpoint: {}", response.getData().toString());

        return response;
    }


    // Call Student Microservice to get all students and return the response to the frontend
    public ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> getAllStudentsWithLimitedDetails() {

        log.info("Get all students method called in BFFStudentService, will call Student MicroService to get all students");

        ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> response = studentServiceClient.getAllStudentsWithLimitedDetails();

        log.info("Received response from backend get all students endpoint: {}", response.getData().toString());

        return response;

    }

    // Call Student Microservice to get a student by ID and return the response to the frontend
    public BFFLimitedStudentResponseDTO getStudentById(BFFStudentRequestDTO bffStudentRequestDTO) {

            log.info("Get student by ID method called in BFFStudentService, will call Student MicroService to get a student by ID: {}",
                    bffStudentRequestDTO.getStudentId());

            BackendStudentResponseDTO student = studentServiceClient.getStudentById(bffStudentRequestDTO)
                    .getData();

            log.info("Received response from Student Service: {}", student.toString());

            // Extract the courseUUID from the student response
            BFFCourseRequestByUUIDDTO courseUUID = BFFCourseRequestByUUIDDTO.builder()
                    .courseUUID(student.getCourseId())
                    .build();

            String courseId = "No course enrolled";

            if (courseUUID.getCourseUUID() != null) {
                log.info("Extracted courseUUID from student response: {}", courseUUID);

                courseId = courseServiceClient.getCourseByUUID(courseUUID)
                                .getData()
                                .getCourseId();

                log.info("Retrieved course ID from course service: {}", courseId);

            } else {
                log.info("No courseUUID found in student response, skipping course ID retrieval");
            }

            // Extracting only the needed details to return to the frontend, we don't want to return the entire backend response which may contain more details than needed
            return BFFLimitedStudentResponseDTO.builder()
                    .studentId(student.getStudentId())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .email(student.getEmail())
                    .dob(student.getDob())
                    .enrollmentDate(student.getEnrollmentDate())
                    .studentStatus(student.getStudentStatus())
                    .courseId(courseId)
                    .build();

    }

    // Call Student + Course Microservices to register a student into a course
    public ApiResponse<BFFLimitedStudentResponseDTO> enrollStudentToCourseServiceMethod(BFFRegisterStudentToCourseIDRequestDTO requestDTO) {

        log.info("Enroll student to course method called in BFFStudentService, will call Course MicroService and Student MicroService through the Saga Orchestrator Service to enroll student with ID: {} to course with ID: {}",
                requestDTO.getStudentId(), requestDTO.getCourseId());

        return sagaServiceClient.enrollStudentToCourseClient(requestDTO);
    }



}
