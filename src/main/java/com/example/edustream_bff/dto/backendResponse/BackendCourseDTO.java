package com.example.edustream_bff.dto.backendResponse;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BackendCourseDTO {

    private UUID id;
    private String courseId;
    private String courseName;
    private int durationDays;
    private String badge;
    private int enrolledStudentsCount;
    private String courseStatus;

}
