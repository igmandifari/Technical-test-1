package com.igman.technicaltest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private Integer currentPage;
    private Integer totalPages;
    private Long count;
    private Integer page;
    private Integer size;
}
