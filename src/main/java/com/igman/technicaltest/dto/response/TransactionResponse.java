package com.igman.technicaltest.dto.response;

import com.igman.technicaltest.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String transactionId;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private TransactionType transactionType;
}
