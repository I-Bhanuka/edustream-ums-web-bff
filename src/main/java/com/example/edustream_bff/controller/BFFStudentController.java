package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.backendResponse.BackendConvocationAddResponse;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFConvocationRequest;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentToCourseIDRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_lib_common.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.service.BFFStudentService;
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
@RequestMapping("api/students")
@Tag(name = "BFF Student Controller", description = "Endpoints for calling the students service through the BFF layer.")
@SecurityRequirement(name = "bearerAuth")
public class BFFStudentController {

    private final BFFStudentService bffStudentService;

    @PostMapping("/register")
    @Operation(summary = "Create a new student by calling the Student Microservice's student creation endpoint. Returns the created student's details if successful.")
    public ResponseEntity<ApiResponse<BFFRegisterStudentResponseDTO>> registerStudent(@Valid @RequestBody BFFRegisterStudentRequestDTO bffRegisterStudentRequestDTO) {

        ApiResponse<BFFRegisterStudentResponseDTO> response = bffStudentService.registerStudent(bffRegisterStudentRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/allWithLimitedDetails")
    @Operation(summary = "Get a paginated list of all students with limited details by calling the Student Microservice's endpoint for fetching all students with limited details. Returns a paginated response containing limited student details.")
    public ResponseEntity<ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>>> getAllStudentsWithLimitedDetails() {

        ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> response = bffStudentService.getAllStudentsWithLimitedDetails();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping("/getStudentById")
    @Operation(summary = "Get student details by student ID by calling the Student Microservice's endpoint for fetching a student by their ID. Returns the student details if found.")
    public ResponseEntity<ApiResponse<BFFLimitedStudentResponseDTO>> getStudentById(@Valid @RequestBody BFFStudentRequestDTO bffStudentRequestDTO) {

        BFFLimitedStudentResponseDTO response = bffStudentService.getStudentById(bffStudentRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<BFFLimitedStudentResponseDTO>builder()
                        .success(true)
                        .message("Student details retrieved successfully")
                        .data(response)
                        .build());
    }


    @PostMapping("/enrollToCourse")
    @Operation(summary = "Enroll a student to a course by calling the Student Microservice's endpoint for enrolling a student to a course. Returns the updated student details with the newly enrolled course if successful.")
    public ResponseEntity<ApiResponse<BFFLimitedStudentResponseDTO>> enrollStudentToCourseEndpoint(
            @Valid @RequestBody BFFRegisterStudentToCourseIDRequestDTO requestDTO) {

        ApiResponse<BFFLimitedStudentResponseDTO> response = bffStudentService.enrollStudentToCourseServiceMethod(requestDTO);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/createConvocation")
    @Operation(summary = "Create a convocation by calling the Student Microservice's endpoint for creating a convocation. Returns the created convocation details if successful.")
    public ResponseEntity<ApiResponse<BackendConvocationAddResponse>> createConvocation(@Valid @RequestBody BFFConvocationRequest bffConvocationRequest) {

        ApiResponse<BackendConvocationAddResponse> response = bffStudentService.createConvocation(bffConvocationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
