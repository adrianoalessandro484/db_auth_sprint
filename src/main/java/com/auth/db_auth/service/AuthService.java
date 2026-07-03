package com.auth.db_auth.service;

import com.auth.db_auth.dto.LoginRequest;
import com.auth.db_auth.dto.UsuarioResponse;

import java.util.List;
import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginRequest request);
    UsuarioResponse register(LoginRequest request, String fullName, String role);
    List<UsuarioResponse> findAll();
}