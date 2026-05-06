package com.example.edustream_bff.dto.backendResponse;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackendStudentResponseDTO {

    private String firstName;
    private  String lastName;
    private String email;
    private LocalDate dob;
    private LocalDate enrollmentDate;
    private String studentStatus;
    private BackendCourseDTO course;


}
