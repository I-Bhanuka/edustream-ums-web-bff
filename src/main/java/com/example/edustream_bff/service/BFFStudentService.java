package com.example.edustream_bff.service;

import com.example.edustream_bff.client.CourseServiceClient;
import com.example.edustream_bff.client.SagaServiceClient;
import com.example.edustream_bff.client.StudentServiceClient;
import com.example.edustream_bff.dto.backendResponse.BackendConvocationAddResponse;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.*;
import com.example.edustream_bff.dto.responseDTO.BFFConvocationResponse;
import com.example.edustream_bff.dto.responseDTO.BFFManageConvocationResponse;
import com.example.edustream_lib_common.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
                    .academicsStanding(student.getAcademicsStanding())
                    .currentGpa(student.getCurrentGpa())
                    .comultativeGpa(student.getComultativeGpa())
                    .build();

    }

    // Call Student MS to create a convocation and return the response to the frontend
    public ApiResponse<BackendConvocationAddResponse> createConvocation(BFFConvocationRequest  bffConvocationRequest) {

        log.info("Create convocation method called in BFFStudentService, will call Student MicroService to create a convocation");

        ApiResponse<BackendConvocationAddResponse> response = studentServiceClient.createConvocation(bffConvocationRequest);

        log.info("Received response from backend create convocation endpoint: {}", response.getData().toString());

        return response;
    }

    // Call student MS to get all convocations and return the response to the frontend
    public ApiResponse<List<BFFConvocationResponse>> getAllConvocations() {

        log.info("Get all convocations method called in BFFStudentService, will call Student MicroService to get all convocations");

        ApiResponse<List<BFFConvocationResponse>> response = studentServiceClient.getAllConvocations();

        log.info("Received response from backend get all convocations endpoint: {}", response.getData().toString());

        return response;
    }

    // Call Student MS to search convocations and return the response to the frontend
    public ApiResponse<List<BFFManageConvocationResponse>> searchConvocations(BFFManageConvocationRequest request) {

        log.info("Search convocations method called in BFFStudentService, will call Student MicroService to search convocations with criteria - Convocation Name: {}, Year: {}, Status: {}",
                request.getConvocationName(), request.getConvocationYear(), request.getConvocationStatus());

        ApiResponse<List<BFFManageConvocationResponse>> response = studentServiceClient.searchConvocations(request);

        log.info("Received response from backend search convocations endpoint: {}", response.getData().toString());

        return response;
    }

    // Call Student + Course Microservices to register a student into a course
    public ApiResponse<BFFLimitedStudentResponseDTO> enrollStudentToCourseServiceMethod(BFFRegisterStudentToCourseIDRequestDTO requestDTO) {

        log.info("Enroll student to course method called in BFFStudentService, will call Course MicroService and Student MicroService through the Saga Orchestrator Service to enroll student with ID: {} to course with ID: {}",
                requestDTO.getStudentId(), requestDTO.getCourseId());

        // The Saga Response
        String response = sagaServiceClient.enrollStudentToCourseClient(requestDTO)
                .getData();

        // Return the Student Details as a response body
        BFFLimitedStudentResponseDTO student = getStudentById(BFFStudentRequestDTO.builder()
                .studentId(requestDTO.getStudentId())
                .build());


        return ApiResponse.<BFFLimitedStudentResponseDTO>builder()
                .success(true)
                .message("Student with ID: " + student.getStudentId() + " enrolled to course with ID: " + requestDTO.getCourseId() + " successfully")
                .data(student)
                .build();
    }



}
