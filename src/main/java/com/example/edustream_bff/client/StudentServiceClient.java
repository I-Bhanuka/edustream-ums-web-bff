package com.example.edustream_bff.client;

import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class StudentServiceClient {

    // ResClient bean
    private final RestClient restClient;

    // Endpoints
    @Value("${services.student.api.create}")
    private String studentCreateEndpoint;

    @Value("${services.student.api.getAllLimited}")
    private String studentReadAllEndpoint;

    @Value("${services.student.api.getById}")
    private String studentReadByIdEndpoint;


    public StudentServiceClient(@Qualifier("studentRestClient") RestClient restClient) {
        this.restClient = restClient;
    }


    public ApiResponse<BFFRegisterStudentResponseDTO> registerStudent(BFFRegisterStudentRequestDTO registerStudentRequestDTO) {
        return restClient.post()
                .uri(studentCreateEndpoint)
                .body(registerStudentRequestDTO)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> getAllStudentsWithLimitedDetails() {

        return restClient.post()
                .uri(studentReadAllEndpoint)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public ApiResponse<BackendStudentResponseDTO> getStudentById(BFFStudentRequestDTO studentRequestDTO) {
        return restClient.post()
                .uri(studentReadByIdEndpoint)
                .body(studentRequestDTO)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

}
