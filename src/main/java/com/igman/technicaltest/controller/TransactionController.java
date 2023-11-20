package com.igman.technicaltest.controller;

import com.igman.technicaltest.dto.request.customer.SearchCustomerRequest;
import com.igman.technicaltest.dto.request.transaction.SearchTransactionRequest;
import com.igman.technicaltest.dto.request.transaction.TransactionRequest;
import com.igman.technicaltest.dto.response.CommonResponse;
import com.igman.technicaltest.dto.response.PagingResponse;
import com.igman.technicaltest.dto.response.TransactionResponse;
import com.igman.technicaltest.entity.Customer;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.service.TransactionService;
import com.igman.technicaltest.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
//    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createTransaction(request);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("successfully created new transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(transactionResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<?> getAllTransaction(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);

        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<TransactionResponse> transactionResponses = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(transactionResponses.getTotalElements())
                .totalPages(transactionResponses.getTotalPages())
                .build();

        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .message("successfully get all transaction")
                .statusCode(HttpStatus.OK.value())
                .data(transactionResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getById(id);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("successfully get transaction by id")
                .statusCode(HttpStatus.OK.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
