package com.igman.technicaltest.service.impl;

import com.igman.technicaltest.dto.request.transaction.SearchTransactionRequest;
import com.igman.technicaltest.dto.request.transaction.TransactionRequest;
import com.igman.technicaltest.dto.response.TransactionResponse;
import com.igman.technicaltest.entity.Customer;
import com.igman.technicaltest.entity.Transaction;
import com.igman.technicaltest.repository.TransactionRepository;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.service.TransactionService;
import com.igman.technicaltest.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        validationUtil.validate(request);

        Customer customer = customerService.getById(request.getCustomerId());

        Transaction transaction = new Transaction();
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setCustomer(customer);


        if ("DEPOSIT".equals(request.getTransactionType())) {
            BigDecimal depositAmount = new BigDecimal(String.valueOf(request.getAmount()));
            customer.getAccount().setBalance(
                    customer.getAccount().getBalance().add(depositAmount)
            );
        } else if ("WITHDRAW".equals(request.getTransactionType())) {
            BigDecimal withdrawalAmount = new BigDecimal(String.valueOf(request.getAmount()));
            if (customer.getAccount().getBalance().compareTo(withdrawalAmount) >= 0) {
                customer.getAccount().setBalance(
                        customer.getAccount().getBalance().subtract(withdrawalAmount)
                );
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Saldo tidak cukup");
            }
        }


        customerService.updateCustomer(customer);
        Transaction savedTransaction = transactionRepository.save(transaction);


        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(savedTransaction.getId());
        transactionResponse.setTransactionDate(String.valueOf(savedTransaction.getTimestamp()));
        transactionResponse.setAmount(savedTransaction.getAmount());
        transactionResponse.setTransactionType(savedTransaction.getTransactionType());

        return transactionResponse;
    }

    @Override
    public Page<TransactionResponse> getAll(SearchTransactionRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Transaction> orders = transactionRepository.findAll(pageable);

        return orders.map(transaction -> mapTransactionToResponse(transaction));
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found"));
        return mapTransactionToResponse(transaction);
    }

    private TransactionResponse mapTransactionToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getId());
        response.setTransactionDate(String.valueOf(transaction.getTimestamp()));
        response.setAmount(transaction.getAmount());
        response.setTransactionType(transaction.getTransactionType());
        return response;
    }
}
