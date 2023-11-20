package com.igman.technicaltest.dto.request.transaction;

import com.igman.technicaltest.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal amount;
    private String customerId;

}
