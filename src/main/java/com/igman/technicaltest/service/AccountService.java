package com.igman.technicaltest.service;

import com.igman.technicaltest.dto.request.account.AccountRequest;
import com.igman.technicaltest.dto.response.AccountResponse;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountResponse create(AccountRequest request);
}
