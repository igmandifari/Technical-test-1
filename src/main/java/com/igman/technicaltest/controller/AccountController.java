package com.igman.technicaltest.controller;

import com.igman.technicaltest.dto.request.account.AccountRequest;
import com.igman.technicaltest.dto.request.customer.CustomerRequest;
import com.igman.technicaltest.dto.response.AccountResponse;
import com.igman.technicaltest.dto.response.CommonResponse;
import com.igman.technicaltest.dto.response.CustomerResponse;
import com.igman.technicaltest.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    @PostMapping
    public ResponseEntity<?> addAccount(@RequestBody AccountRequest request) {
        AccountResponse accountResponse = accountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<AccountResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully add balance")
                        .data(accountResponse)
                        .build());
    }
}
