# db_auth — Microservicio de Autenticación

## ¿Qué hace?
Servicio responsable del **login, registro y listado de usuarios** del ERP Contreras.
Almacena credenciales locales en su propia base de datos `db_auth`.

## Stack
- Spring Boot 4.1.0
- Spring Web MVC (`spring-boot-starter-webmvc`)
- Spring Data JPA + Hibernate
- Spring Security (BCrypt para passwords)
- Spring Cloud Netflix Eureka (cliente)

## Base de datos
- Contenedor: `erp-db-auth-postgres` (postgres:16-alpine)
- DB: `db_auth` (puerto host `5433` → contenedor `5432`)
- Usuario: `adriano`
- Password: `password123`

Las credenciales están **hardcodeadas** en `application.properties` (no usa `.env`).

## Tablas que crea
- `usuarios` (id, email UNIQUE, password_hash, full_name, role, activo, created_at)

## Endpoints
| Método | Ruta                | Descripción                              |
|--------|---------------------|------------------------------------------|
| POST   | `/api/auth/login`   | Login. Devuelve `{user, token}`          |
| POST   | `/api/auth/register`| Registro básico de usuario               |
| GET    | `/api/auth/usuarios`| Lista todos los usuarios                 |
| GET    | `/api/auth/health`  | Healthcheck del servicio                 |

## Cómo levantarlo
```bash
./mvnw clean package -DskipTests
docker compose up -d
```
Esto levanta Postgres (`5433`) y el microservicio (`8081`) juntos.

## Puerto
`8081`

## Estructura
```
db_auth/
├── pom.xml
├── src/main/java/com/auth/db_auth/
│   ├── DbAuthApplication.java
│   ├── config/SecurityConfig.java
│   ├── controller/AuthController.java
│   ├── dto/LoginRequest.java
│   ├── dto/UsuarioResponse.java
│   ├── entity/Usuario.java
│   ├── repository/UsuarioRepository.java
│   └── service/impl/AuthServiceImpl.java
└── src/main/resources/application.properties
```