package com.example.edustream_bff.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BFFCourseRequestByIdDTO {

    @NotBlank(message = "courseId is required")
    private  String courseId;
}
