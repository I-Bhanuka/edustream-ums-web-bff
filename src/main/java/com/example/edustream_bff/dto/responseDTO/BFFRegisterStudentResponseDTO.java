package com.example.edustream_bff.dto.responseDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BFFRegisterStudentResponseDTO {

    private String studentId;

    private String firstName;

    private String lastName;

    private String dob;

    private String email;
}
