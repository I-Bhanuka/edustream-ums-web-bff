package com.example.edustream_bff.dto.responseDTO;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BFFManageConvocationResponse {

    private UUID convocationId;

    private String convocationName;

    private short convocationYear;

    private String convocationStatus;

}
