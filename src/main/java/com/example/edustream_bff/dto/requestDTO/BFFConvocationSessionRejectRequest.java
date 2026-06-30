package com.example.edustream_bff.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFConvocationSessionRejectRequest {

    @NotNull(message = "Session Reject Remarks is required")
    @NotBlank(message = "Session Reject Remarks cannot be blank")
    private String sessionRejectRemarks;
}
