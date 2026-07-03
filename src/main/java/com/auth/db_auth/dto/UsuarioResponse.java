package com.auth.db_auth.dto;

import com.auth.db_auth.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String email;
    private String fullName;
    private String role;
    private Boolean activo;
    private LocalDateTime createdAt;

    public static UsuarioResponse fromEntity(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getEmail(),
                u.getFullName(),
                u.getRole(),
                u.getActivo(),
                u.getCreatedAt()
        );
    }
}