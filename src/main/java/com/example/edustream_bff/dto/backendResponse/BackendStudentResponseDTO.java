package com.example.edustream_bff.dto.backendResponse;

import com.example.edustream_bff.enums.BackendStudentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackendStudentResponseDTO {

    private UUID id;

    private String studentId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String email;

    private LocalDate enrollmentDate;

    private BackendStudentStatus studentStatus;

    private UUID courseUUID;

    private LocalDateTime createdAt;

}
