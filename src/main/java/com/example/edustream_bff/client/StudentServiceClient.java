package com.example.edustream_bff.client;

import com.example.edustream_bff.dto.backendResponse.BackendConvocationAddResponse;
import com.example.edustream_bff.dto.backendResponse.BackendLimitedStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.BackendStudentResponseDTO;
import com.example.edustream_bff.dto.backendResponse.PageResponseDTO;
import com.example.edustream_bff.dto.requestDTO.BFFConvocationRequest;
import com.example.edustream_bff.dto.requestDTO.BFFManageConvocationRequest;
import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentRequestDTO;
import com.example.edustream_bff.dto.requestDTO.BFFStudentRequestDTO;
import com.example.edustream_bff.dto.responseDTO.BFFConvocationResponse;
import com.example.edustream_bff.dto.responseDTO.BFFManageConvocationResponse;
import com.example.edustream_lib_common.responseDTO.ApiResponse;
import com.example.edustream_bff.dto.responseDTO.BFFRegisterStudentResponseDTO;
import com.example.edustream_bff.exception.StudentMicroServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;


@Component
@Slf4j
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

    @Value("${services.student.api.createConvocation}")
    private String convocationCreateEndpoint;

    @Value("${services.student.api.getAllConvocations}")
    private String convocationGetAllEndpoint;

    @Value("${services.student.api.searchConvocations}")
    private String convocationSearchEndpoint;

    @Value("${services.student.api.getConvocationById}")
    private String convocationGetByIdEndpoint;


    public StudentServiceClient(@Qualifier("studentRestClient") RestClient restClient) {
        this.restClient = restClient;
    }


    public ApiResponse<BFFRegisterStudentResponseDTO> registerStudent(BFFRegisterStudentRequestDTO registerStudentRequestDTO) {

        try {
            log.info("Calling the Student Service's {} URI to register a student", studentCreateEndpoint);

            return restClient.post()
                    .uri(studentCreateEndpoint)
                    .body(registerStudentRequestDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (
            HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for student registration: {} - {}", e.getStatusCode().value(),
                downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                e.getStatusCode().value(),
                downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (
        HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when student registration: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to register a student: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

    public ApiResponse<PageResponseDTO<BackendLimitedStudentResponseDTO>> getAllStudentsWithLimitedDetails() {

        try {
            log.info("Calling the Student Service's {} URI to get all students with limited details", studentCreateEndpoint);

            return restClient.post()
                    .uri(studentReadAllEndpoint)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to get all students: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when retrieval of all students: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.", e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to retrieve all students: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

    public ApiResponse<BackendStudentResponseDTO> getStudentById(BFFStudentRequestDTO studentRequestDTO) {

        try {

            log.info("Calling the Student Service's {} URI to get the student of ID: {}", studentReadByIdEndpoint, studentRequestDTO.getStudentId());

            return restClient.post()
                    .uri(studentReadByIdEndpoint)
                    .body(studentRequestDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to get student by id: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when retrieval a student: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to retrieve a student: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

    public ApiResponse<BackendConvocationAddResponse> createConvocation(BFFConvocationRequest bffConvocationRequest) {

        try {
            log.info("Calling the Student Service's {} URI to create a convocation", convocationCreateEndpoint);

            return restClient.post()
                    .uri(convocationCreateEndpoint)
                    .body(bffConvocationRequest)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to create convocation: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when creating convocation: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to create convocation: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

    public ApiResponse<List<BFFConvocationResponse>> getAllConvocations() {

        try {
            log.info("Calling the Student Service's {} URI to get all convocations", convocationGetAllEndpoint);

            return restClient.get()
                    .uri(convocationGetAllEndpoint)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to get all convocations: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when retrieval of all convocations: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.", e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to retrieve all convocations: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

    public ApiResponse<List<BFFManageConvocationResponse>> searchConvocations(BFFManageConvocationRequest bffManageConvocationRequest) {

        try {
            log.info("Calling the Student Service's {} URI to search convocations with criteria - Convocation Name: {}, Year: {}, Status: {}",
                    convocationSearchEndpoint, bffManageConvocationRequest.getConvocationName(),
                    bffManageConvocationRequest.getConvocationYear(),
                    bffManageConvocationRequest.getConvocationStatus());

            return restClient.post()
                    .uri(convocationSearchEndpoint)
                    .body(bffManageConvocationRequest)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to search convocations: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when searching convocations: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to search convocations: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }


    public ApiResponse<BFFConvocationResponse> getConvocationById(UUID convocationId) {

        try {
            log.info("Calling the Student Service's {} URI to get convocation by ID: {}", convocationGetByIdEndpoint, convocationId);

            return restClient.get()
                    .uri(convocationGetByIdEndpoint + convocationId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            // The MS thinks that the BFF made a mistake - 4xx errors
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Student MS for to get convocation by ID: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS has an issue processing the request - 5xx errors
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Student MS when retrieving convocation by ID: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new StudentMicroServiceException("Student Service encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Student MS to retrieve convocation by ID: {}", e.getMessage());
            throw new StudentMicroServiceException("Cannot reach Student Service", 503);
        }
    }

}
