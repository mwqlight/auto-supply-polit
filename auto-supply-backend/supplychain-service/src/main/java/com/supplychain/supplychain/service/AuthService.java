package com.supplychain.supplychain.service;

import com.supplychain.supplychain.dto.request.LoginRequest;
import com.supplychain.supplychain.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    void logout(String token);
}