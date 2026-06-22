package com.example.edustream_bff.dto.responseDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFConvocationResponse {

    private UUID convocationId;

    private String convocationName;

    private int convocationYear;

    private double convocationPayment;

    private LocalDate supplicantOpenDate;

    private LocalDate supplicantEndDate;
}
