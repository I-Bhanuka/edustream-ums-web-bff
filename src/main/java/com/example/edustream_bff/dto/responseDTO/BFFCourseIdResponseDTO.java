package com.example.edustream_bff.dto.responseDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BFFCourseIdResponseDTO {

    @NotBlank(message = "Course ID cannot be blank")
    private String courseId;
}
