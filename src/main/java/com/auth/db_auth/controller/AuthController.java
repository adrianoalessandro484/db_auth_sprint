package com.auth.db_auth.controller;

import com.auth.db_auth.dto.LoginRequest;
import com.auth.db_auth.dto.UsuarioResponse;
import com.auth.db_auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody Map<String, String> body) {
        LoginRequest req = new LoginRequest(body.get("email"), body.get("password"));
        return ResponseEntity.ok(authService.register(req, body.get("fullName"), body.get("role")));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponse>> list() {
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "db_auth"));
    }
}