package com.example.edustream_bff.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BFFStudentRequestDTO {

    @NotBlank(message = "Student ID is required")
    private String studentId;
}
