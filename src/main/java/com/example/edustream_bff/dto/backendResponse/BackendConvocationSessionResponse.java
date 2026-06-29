package com.example.edustream_bff.dto.backendResponse;

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
public class BackendConvocationSessionResponse {

    private UUID convocationSessionId;

    private UUID convocationId;

    private String sessionName;

    private LocalDate sessionDate;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Short capacity;

    private Short noOfPasses;

    private Short noOfStaff;

    private String chiefGuest;

    private String convocationSessionStatus;


}
