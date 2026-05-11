package com.example.edustream_bff.dto.responseDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFRegisterCourseResponseDTO {
    private String courseId;

    private String courseName;

    private int durationDays;

    private String badge;

    private int enrolledStudentsCount;

}
