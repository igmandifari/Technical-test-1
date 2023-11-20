package com.igman.technicaltest.service.impl;

import com.igman.technicaltest.dto.request.account.AccountRequest;
import com.igman.technicaltest.dto.response.AccountResponse;
import com.igman.technicaltest.entity.Account;
import com.igman.technicaltest.repository.AcountRepository;
import com.igman.technicaltest.repository.CustomerRepository;
import com.igman.technicaltest.service.AccountService;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ValidationUtil validationUtil;
    private final AcountRepository acountRepository;

    @Override
    public AccountResponse create(AccountRequest request) {
        try {
            validationUtil.validate(request);
            Account account = Account.builder()
                    .id(request.getCustomerId())
                    .balance(BigDecimal.valueOf(request.getBalance()))
                    .build();
            acountRepository.saveAndFlush(account);
            return mapToResponse(account);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .customerId(String.valueOf(account.getCustomerId()))
                .balance(String.valueOf(account.getBalance()))
                .build();
    }
}
