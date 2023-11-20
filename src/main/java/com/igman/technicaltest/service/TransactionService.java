package com.igman.technicaltest.service;

import com.igman.technicaltest.dto.request.transaction.SearchTransactionRequest;
import com.igman.technicaltest.dto.request.transaction.TransactionRequest;
import com.igman.technicaltest.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    Page<TransactionResponse> getAll(SearchTransactionRequest request);
    TransactionResponse getById(String id);
}
