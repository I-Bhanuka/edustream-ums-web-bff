package com.example.edustream_bff.dto.backendResponse;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class PageResponseDTO<T> {

    @ToString.Include
    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
