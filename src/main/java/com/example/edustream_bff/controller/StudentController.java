package com.example.edustream_bff.controller;

import com.example.edustream_bff.dto.WebStudentDTO;
import com.example.edustream_bff.service.StudentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/bff/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<List<WebStudentDTO>> getAllStudents(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        log.info("Received request to get all students");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            log.warn("Authorization header was null or empty");
            return ResponseEntity.badRequest().build();
        }

        List<WebStudentDTO> students = studentService.getAllStudentsForWeb(authorizationHeader);

        return ResponseEntity.ok(students);

    }
}
