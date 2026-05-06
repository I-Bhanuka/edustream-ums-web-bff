package com.example.edustream_bff.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebStudentDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String courseName;

}
