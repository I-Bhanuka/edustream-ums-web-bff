package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByIdDTO;
import com.example.edustream_bff.dto.requestDTO.BFFCourseRequestByUUIDDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterCourseRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterCourseResponseDTO;
import com.example.edustream_bff.service.BFFCourseService;
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
public class BFFCourseController {

    private final BFFCourseService bffCourseService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<BFFRegisterCourseResponseDTO>> registerCourse(
            @Valid @RequestBody BFFRegisterCourseRequestDTO bffRegisterCourseRequestDTO) {

        ApiResponse<BFFRegisterCourseResponseDTO> response = bffCourseService.registerCourse(bffRegisterCourseRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<PageResponseDTO<Object>>> getAllCourses() {

        ApiResponse<PageResponseDTO<Object>> response = bffCourseService.getAllCourses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping("/getCourseById")
    public ResponseEntity<ApiResponse<Object>> getCourseById(@Valid @RequestBody BFFCourseRequestByIdDTO bffCourseRequestByIdDTO) {

        ApiResponse<Object> response = bffCourseService.getCourseById(bffCourseRequestByIdDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/getCourseByUUID")
    public ResponseEntity<ApiResponse<Object>> getCourseByUUID(@Valid @RequestBody BFFCourseRequestByUUIDDTO bffCourseRequestByUUIDDTO) {

        ApiResponse<Object> response = bffCourseService.getCourseByUUID(bffCourseRequestByUUIDDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
