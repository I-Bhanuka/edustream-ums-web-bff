package com.example.edustream_bff.dto.requestDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFManageConvocationRequest {

    private String convocationName;

    private Short convocationYear;

    private String convocationStatus;
}
