package com.auth.db_auth.service.impl;

import com.auth.db_auth.dto.LoginRequest;
import com.auth.db_auth.dto.UsuarioResponse;
import com.auth.db_auth.entity.Usuario;
import com.auth.db_auth.repository.UsuarioRepository;
import com.auth.db_auth.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> login(LoginRequest request) {
        Usuario u = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(u.getActivo())) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), u.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", UsuarioResponse.fromEntity(u));
        response.put("token", "local-" + u.getId());
        return response;
    }

    @Override
    @Transactional
    public UsuarioResponse register(LoginRequest request, String fullName, String role) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
        Usuario u = Usuario.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(fullName != null ? fullName.toUpperCase() : "")
                .role(role != null ? role : "trabajador")
                .activo(true)
                .build();
        Usuario saved = usuarioRepository.save(u);
        return UsuarioResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }
}