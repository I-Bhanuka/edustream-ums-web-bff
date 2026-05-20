package com.example.edustream_bff.dto.backendResponse;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BackendLimitedStudentResponseDTO {

    private String studentId;

    private String firstName;

    private  String lastName;

    private String email;

    private LocalDate dob;

    private LocalDate enrollmentDate;

    private String studentStatus;

    private UUID courseUUID;

    private String academicsStanding;

    private double currentGpa;

    private double comultativeGpa;


}
