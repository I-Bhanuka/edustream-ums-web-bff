package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.backendResponse.BackendCourseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByIdDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByUUIDDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterCourseRequestDTO;
import com.example.edustream_lib_common.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFCourseIdResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterCourseResponseDTO;
import com.example.edustream_bff.service.BFFCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/courses")
@Tag(name = "BFF Course Controller", description = "Endpoints for calling the courses service through the BFF layer.")
@SecurityRequirement(name = "bearerAuth")
public class BFFCourseController {

    private final BFFCourseService bffCourseService;

    @PostMapping("/register")
    @Operation(summary = "Create a new course by calling the Course Microservice's course creation endpoint. Returns the created course's details if successful.")
    public ResponseEntity<ApiResponse<BFFRegisterCourseResponseDTO>> registerCourse(
            @Valid @RequestBody BFFRegisterCourseRequestDTO bffRegisterCourseRequestDTO) {

        ApiResponse<BFFRegisterCourseResponseDTO> response = bffCourseService.registerCourse(bffRegisterCourseRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/all")
    @Operation(summary = "Get a paginated list of all courses by calling the Course Microservice's endpoint for fetching all courses. Returns a paginated response containing course details.")
    public ResponseEntity<ApiResponse<PageResponseDTO<Object>>> getAllCourses() {

        ApiResponse<PageResponseDTO<Object>> response = bffCourseService.getAllCourses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping("/getCourseById")
    @Operation(summary = "Get course details by course ID by calling the Course Microservice's endpoint for fetching a course by its ID. Returns the course details if found.")
    public ResponseEntity<ApiResponse<Object>> getCourseById(@Valid @RequestBody BFFCourseRequestByIdDTO bffCourseRequestByIdDTO) {

        ApiResponse<Object> response = bffCourseService.getCourseById(bffCourseRequestByIdDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/getCourseByUUID")
    @Operation(summary = "Get course details by course UUID by calling the Course Microservice's endpoint for fetching a course by its UUID. Returns the course details if found.")
    public ResponseEntity<ApiResponse<BackendCourseDTO>> getCourseByUUID(@Valid @RequestBody BFFCourseRequestByUUIDDTO bffCourseRequestByUUIDDTO) {

        ApiResponse<BackendCourseDTO> response = bffCourseService.getCourseByUUID(bffCourseRequestByUUIDDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/getCourseByUUIDForCourseId")
    @Operation(summary = "Get course ID by course UUID by calling the Course Microservice's endpoint for fetching a course by its UUID. Returns the course ID if found.")
    public ResponseEntity<ApiResponse<BFFCourseIdResponseDTO>> getCourseByUUIDForCourseId(@Valid @RequestBody BFFCourseRequestByUUIDDTO bffCourseRequestByUUIDDTO) {

        ApiResponse<BFFCourseIdResponseDTO> response = bffCourseService.getCourseIdByUUID(bffCourseRequestByUUIDDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
