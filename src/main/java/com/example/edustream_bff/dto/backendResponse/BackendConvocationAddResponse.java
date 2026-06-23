package com.example.edustream_bff.dto.backendResponse;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BackendConvocationAddResponse {

    private UUID convocationId;

    private String convocationName;

    private short convocationYear;

    private BigDecimal convocationPayment;

    private LocalDate supplicantOpenDate;

    private LocalDate supplicantEndDate;
}
