package com.example.edustream_bff.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFConvocationRequest {

    @NotBlank(message = "Convocation name is required")
    @NotNull(message = "Convocation name cannot be null")
    private String convocationName;

    @NotNull(message = "Convocation description cannot be null")
    private short convocationYear;

    @NotNull(message = "Convocation payment cannot be null")
    private BigDecimal convocationPayment;

    @NotNull(message = "Supplicant open date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate supplicantOpenDate;

    @NotNull(message = "Supplicant end date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate supplicantEndDate;


}
