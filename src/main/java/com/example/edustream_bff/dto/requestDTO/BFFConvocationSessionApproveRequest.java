package com.example.edustream_bff.dto.requestDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFConvocationSessionApproveRequest {

    private String sessionApprovalRemarks;
}
