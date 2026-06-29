package com.example.edustream_bff.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFConvocationSessionRequest {

    @NotNull(message = "Convocation ID is required")
    private UUID convocationId;

    @NotNull(message = "Session name is required")
    @NotBlank(message = "Session name cannot be blank")
    private String sessionName;

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;

    @NotNull(message = "From time is required")
    private LocalDateTime fromTime;

    @NotNull(message = "To time is required")
    private LocalDateTime toTime;

    @NotNull(message = "Capacity is required")
    private Short capacity;

    private Short noOfPasses;

    private Short noOfStaff;

    private String chiefGuest;

}
