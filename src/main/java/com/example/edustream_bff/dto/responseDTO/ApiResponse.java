package com.example.edustream_bff.dto.responseDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
