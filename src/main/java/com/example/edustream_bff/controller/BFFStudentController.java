package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.backendResponse.BackendConvocationAddResponse;
import com.example.edustream_bff.dto.backendResponse.BackendConvocationSessionResponse;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.requestDTO.*;
import com.example.edustream_bff.dto.responseDTO.BFFConvocationResponse;
import com.example.edustream_bff.dto.responseDTO.BFFManageConvocationResponse;
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

import java.util.List;
import java.util.UUID;


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

    @GetMapping
    @Operation(summary = "Get all convocations by calling the Student Microservice's endpoint for fetching all convocations. Returns a list of all convocations with their details.")
    public ResponseEntity<ApiResponse<List<BFFConvocationResponse>>> getAllConvocations() {

        ApiResponse<List<BFFConvocationResponse>> response = bffStudentService.getAllConvocations();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @PostMapping("/search")
    @Operation(summary = "Search convocations by calling the Student Microservice's endpoint for searching convocations based on criteria. Returns a list of convocations that match the search criteria.")
    public ResponseEntity<ApiResponse<List<BFFManageConvocationResponse>>> searchConvocations(
            @Valid @RequestBody BFFManageConvocationRequest request) {

        ApiResponse<List<BFFManageConvocationResponse>> response = bffStudentService.searchConvocations(request);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get convocation by ID by calling the Student Microservice's endpoint for fetching a convocation by its ID. Returns the convocation details if found.")
    public ResponseEntity<ApiResponse<BFFConvocationResponse>> getConvocationById(@PathVariable UUID id) {

        ApiResponse<BFFConvocationResponse> response = bffStudentService.getConvocationById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/convocationNames/all")
    @Operation(summary = "Get all convocation names by calling the Student Microservice's endpoint for fetching all convocation names. Returns a list of all convocation names.")
    public ResponseEntity<ApiResponse<List<String>>> getAllConvocationNames() {

        ApiResponse<List<String>> response = bffStudentService.getAllConvocationNames();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/convocationNames/{name}")
    @Operation(summary = "Get convocation names filtered by name by calling the Student Microservice's endpoint for fetching convocation names that match the given name. Returns a list of matching convocation names.")
    public ResponseEntity<ApiResponse<List<String>>> getConvocationNames(@PathVariable String name) {

        ApiResponse<List<String>> response = bffStudentService.getConvocationNames(name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @PostMapping("/convocation-session/create")
    @Operation(summary = "Create a convocation session by calling the Student Microservice's endpoint for creating a convocation session. Returns the created convocation session details if successful.")
    public ResponseEntity<ApiResponse<BackendConvocationSessionResponse>> createConvocationSession(
            @Valid @RequestBody BFFConvocationSessionRequest request) {

        ApiResponse<BackendConvocationSessionResponse> response = bffStudentService.createConvocationSession(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/convocation-session/convocation-sesssions/{convocationId}")
    @Operation(summary = "Get convocation sessions by convocation ID by calling the Student Microservice's endpoint for fetching convocation sessions associated with a specific convocation ID. Returns a list of convocation sessions for the given convocation ID.")
    public ResponseEntity<ApiResponse<List<BackendConvocationSessionResponse>>> getConvocationSessionsByConvocationId(
            @PathVariable UUID convocationId) {

        ApiResponse<List<BackendConvocationSessionResponse>> response = bffStudentService.getConvocationSessionsByConvocationId(convocationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @PostMapping("/convocation-session/approve/{sessionId}")
    @Operation(summary = "Approve a convocation session by calling the Student Microservice's endpoint for approving a convocation session. Returns the updated convocation session details if successful.")
    public ResponseEntity<ApiResponse<BackendConvocationSessionResponse>> approveConvocationSession(
            @PathVariable UUID sessionId, @Valid @RequestBody BFFConvocationSessionApproveRequest request) {

        ApiResponse<BackendConvocationSessionResponse> response = bffStudentService.approveConvocationSession(sessionId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/convocation-session/reject/{sessionId}")
    @Operation(summary = "Reject a convocation session by calling the Student Microservice's endpoint for rejecting a convocation session. Returns the updated convocation session details if successful.")
    public ResponseEntity<ApiResponse<BackendConvocationSessionResponse>> rejectConvocationSession(
            @PathVariable UUID sessionId, @Valid @RequestBody BFFConvocationSessionRejectRequest request) {

        ApiResponse<BackendConvocationSessionResponse> response = bffStudentService.rejectConvocationSession(sessionId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
