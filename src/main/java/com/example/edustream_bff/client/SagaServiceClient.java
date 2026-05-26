package com.example.edustream_bff.client;

import com.example.edustream_bff.dto.requestDTO.BFFRegisterStudentToCourseIDRequestDTO;
import com.example.edustream_lib_common.responseDTO.ApiResponse;
import com.example.edustream_bff.exception.SagaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class SagaServiceClient {

    private final RestClient restClient;

    // Endpoints
    @Value("${services.saga.api.enrollStudentToCourse}")
    private String sagaEnrollStudentToCourse;

    public SagaServiceClient(@Qualifier("sagaRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    // This method will call the Saga Orchestrator to enroll a student to a course, which will trigger the saga flow.
    public ApiResponse<String> enrollStudentToCourseClient(BFFRegisterStudentToCourseIDRequestDTO requestDTO) {

        try {
            log.info("Calling the Saga Orchestrator's {} URI to enroll a student into a course", sagaEnrollStudentToCourse);

            return restClient.post()
                    .uri(sagaEnrollStudentToCourse)
                    .body(requestDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            // The MS thinks that the BFF made a mistake - 4xx error
        } catch (HttpClientErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("BFF error when connecting to Saga Orchestrator for Student enrollment to course: {} - {}",
                    e.getStatusCode().value(),
                    downStreamMessage);
            throw new SagaException("Saga rejected the request",
                    e.getStatusCode().value(),
                    downStreamMessage);

            // The MS encountered an error while processing the request - 5xx error
        } catch (HttpServerErrorException e) {
            String downStreamMessage = e.getResponseBodyAsString();
            log.error("Server error from Saga when Student -> Course Enrollment: {} - {}", e.getStatusCode().value(),
                    downStreamMessage);
            throw new SagaException("Saga encountered an error.",
                    e.getStatusCode().value(),
                    downStreamMessage);

        } catch (ResourceAccessException e) {
            log.error("Network error reaching Saga to Student -> Course Enrollment: {}", e.getMessage());
            throw new SagaException("Cannot reach Saga", 503);
        }

    }


}
