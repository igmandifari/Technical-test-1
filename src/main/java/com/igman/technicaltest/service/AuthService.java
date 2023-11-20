package com.igman.technicaltest.service;
import com.igman.technicaltest.dto.request.AuthRequest;
import com.igman.technicaltest.dto.response.LoginResponse;
import com.igman.technicaltest.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(AuthRequest request);
    RegisterResponse registerCustomer(AuthRequest request);
}
