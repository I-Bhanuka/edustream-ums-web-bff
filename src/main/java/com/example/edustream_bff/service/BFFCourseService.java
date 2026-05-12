package com.example.edustream_bff.service;

import com.example.edustream_bff.client.CourseServiceClient;
import com.example.edustream_bff.dto.backendResponse.BackendCourseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByIdDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByUUIDDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterCourseRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFCourseIdResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterCourseResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BFFCourseService {

    private final CourseServiceClient courseServiceClient;

    // Call Course Microservice to create a new course and return the response to the frontend
    public ApiResponse<BFFRegisterCourseResponseDTO> registerCourse(BFFRegisterCourseRequestDTO registerCourseRequestDTO) {

            log.info("Create course method called in BFFCourseService, will call course MicroService to create a new course");

            ApiResponse<BFFRegisterCourseResponseDTO> response = courseServiceClient.registerCourse(registerCourseRequestDTO);

            log.info("Received response from backend create course endpoint: {}", response.getData().toString());

            return response;
    }

    // Call Course Microservice to get all courses and return the response to the frontend
    public ApiResponse<PageResponseDTO<Object>> getAllCourses() {

            log.info("Get all courses method called in BFFCourseService, will call Course MicroService to get all courses");

            ApiResponse<PageResponseDTO<Object>> response = courseServiceClient.getAllCourses();

            log.info("Received response from backend get all courses endpoint: {}", response.getData().toString());

            return response;

    }


    // Call Course Microservice to get a course by ID and return the response to the frontend
    public ApiResponse<Object> getCourseById(BFFCourseRequestByIdDTO bffCourseRequestByIdDTO) {

            log.info("Get course by ID method called in BFFCourseService, will call Course MicroService to get a course by ID: {}",
                    bffCourseRequestByIdDTO.getCourseId());

            ApiResponse<Object> response = courseServiceClient.getCourseById(bffCourseRequestByIdDTO);

            log.info("Received response from backend get course by ID: {}", response.getData().toString());

            return response;

    }

    // Call Course Microservice to get a course by UUID and return the response to the frontend
    public ApiResponse<BackendCourseDTO> getCourseByUUID(BFFCourseRequestByUUIDDTO bffCourseRequestByUUIDDTO) {

        log.info("Get course by UUID method called in BFFCourseService, will call Course MicroService to get a course by UUID: {}",
                bffCourseRequestByUUIDDTO.getCourseUUID());

        ApiResponse<BackendCourseDTO> response = courseServiceClient.getCourseByUUID(bffCourseRequestByUUIDDTO);

        log.info("Received response from backend get course by UUID: {}", response.getData().toString());

        return response;

    }

    // Call Course Microservice to get a course by UUID and extract the course ID from the response, then return the course ID to the frontend
    public ApiResponse<BFFCourseIdResponseDTO> getCourseIdByUUID(BFFCourseRequestByUUIDDTO bffCourseRequestByUUIDDTO) {

        log.info("Get course by UUID method called in BFFCourseService, will call Course MicroService to get a course ID by UUID: {}",
                bffCourseRequestByUUIDDTO.getCourseUUID());

        ApiResponse<BackendCourseDTO> response = courseServiceClient.getCourseByUUID(bffCourseRequestByUUIDDTO);

        BFFCourseIdResponseDTO responseDTO = BFFCourseIdResponseDTO.builder()
                .courseId(response.getData().getCourseId())
                .build();

        log.info("Received response from backend get course Id by UUID: {}", responseDTO.toString());

        return ApiResponse.<BFFCourseIdResponseDTO>builder()
                .success(true)
                .message("Course ID retrieved successfully By the UUID")
                .data(responseDTO)
                .build();

    }


}
