package com.example.edustream_bff.dto.responseDTO;

import com.example.edustream_bff.enums.BackendStudentStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BFFLimitedStudentResponseDTO {

    private String studentId;

    private String firstName;

    private  String lastName;

    private String email;

    private LocalDate dob;

    private LocalDate enrollmentDate;

    private BackendStudentStatus studentStatus;

    private UUID courseUUID;
}
