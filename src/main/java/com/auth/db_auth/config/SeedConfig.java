package com.auth.db_auth.config;

import com.auth.db_auth.entity.Usuario;
import com.auth.db_auth.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SeedConfig {

    private static final Logger log = LoggerFactory.getLogger(SeedConfig.class);

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    @Value("${SEED_ADMIN_EMAIL:admin@contreras.com}")
    private String adminEmail;

    @Value("${SEED_ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    @Value("${SEED_ADMIN_NAME:ADMINISTRADOR}")
    private String adminName;

    @Bean
    public CommandLineRunner seedAdmin(UsuarioRepository repository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (!seedEnabled) {
                return;
            }
            if (repository.existsByEmail(adminEmail)) {
                log.info("Seed: usuario admin '{}' ya existe, se omite", adminEmail);
                return;
            }
            Usuario admin = Usuario.builder()
                    .email(adminEmail)
                    .passwordHash(encoder.encode(adminPassword))
                    .fullName(adminName)
                    .role("admin")
                    .activo(true)
                    .build();
            repository.save(admin);
            log.info("Seed: usuario admin '{}' creado con exito (rol=admin)", adminEmail);
        };
    }
}