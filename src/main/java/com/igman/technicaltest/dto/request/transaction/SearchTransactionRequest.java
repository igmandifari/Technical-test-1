package com.igman.technicaltest.dto.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTransactionRequest {
    private Integer page;
    private Integer size;
}
