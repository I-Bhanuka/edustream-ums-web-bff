package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.service.BFFStudentService;
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
public class BFFStudentController {

    private final BFFStudentService bffStudentService;

//    @GetMapping("/all")
//    public ResponseEntity<List<WebStudentDTO>> getAllStudents(
//            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
//
//        log.info("Received request to get all students");
//
//        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
//            log.warn("Authorization header was null or empty");
//            return ResponseEntity.badRequest().build();
//        }
//
//        List<WebStudentDTO> students = studentService.getAllStudentsForWeb(authorizationHeader);
//
//        return ResponseEntity.ok(students);
//
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<BackendResponseDTO> registerNewStudent(
//            @RequestHeader(value = "Authorization") String authorizationHeader,
//            @RequestBody WebRegisterStudentDTO webRegisterStudentDTO) {
//
//        log.info("WebRegisterStudentDTO received: {}", webRegisterStudentDTO);
//        log.info("Received request to register new student");
//
//        BackendResponseDTO response = studentService.registerNewStudent(authorizationHeader, webRegisterStudentDTO);
//
//        return ResponseEntity.ok(response);
//
//    }

    @GetMapping
    public String testEndpoint() {

        return bffStudentService.testEndpoint();

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<BFFRegisterStudentResponseDTO>> registerStudent(@Valid @RequestBody BFFRegisterStudentRequestDTO bffRegisterStudentRequestDTO) {

        ApiResponse<BFFRegisterStudentResponseDTO> response = bffStudentService.registerStudent(bffRegisterStudentRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/allWithLimitedDetails")
    public ResponseEntity<ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>>> getAllStudentsWithLimitedDetails() {

        ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> response = bffStudentService.getAllStudentsWithLimitedDetails();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping("/getStudentById")
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

}
