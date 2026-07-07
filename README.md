# Sudamericanaprueba2

API REST en Spring Boot para gestión de tareas, comentarios y autenticación JWT.

## Resumen

Este proyecto es un backend Java que usa Spring Boot, Spring Data JPA, Spring Security y JWT. Está diseñado para:

- Registrar y autenticar usuarios administradores.
- Crear y consultar tareas.
- Votar y anular votos en tareas.
- Crear comentarios asociados a tareas y usuarios.
- Proteger rutas con JWT y verificar tokens en base de datos.

## Estructura del proyecto

- `src/main/java/com/example/sudamericanaprueba2`
  - `Sudamericanaprueba2Application.java` - clase principal Spring Boot.
  - `config/` - configuración de seguridad, autenticación y OpenAPI.
  - `controller/` - endpoints REST.
  - `dto/` - objetos de transferencia de datos para peticiones y respuestas.
  - `entity/` - entidades JPA.
  - `filter/` - filtro JWT personalizado.
  - `middlewares/` - manejo global de excepciones y respuesta.
  - `repository/` - repositorios Spring Data JPA.
  - `service/` - lógica de negocio.

## Dependencias clave

- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-webmvc`
- `springdoc-openapi-starter-webmvc-ui`
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
- `lombok`
- `mssql-jdbc`
- `spring-boot-starter-validation`

## Configuración necesaria

Archivo: `src/main/resources/application.properties`

- `spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=sudamericanaprueba2;encrypt=true;trustServerCertificate=true;`
- `spring.datasource.username=sa`
- `spring.datasource.password=root`
- `spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver`
- `spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.show-sql=true`
- `server.port=8081`
- `springdoc.api-docs.path=/v3/api-docs`
- `springdoc.swagger-ui.path=/swagger-ui.html`
- `jwt.secret=...`
- `jwt.expiration=3600000`
- `jwt.refresh.expiration=604800000`

> Nota: Ajusta `spring.datasource.*` y el secreto JWT según tu entorno. No dejes valores sensibles en un repositorio público.

## Ejecución

### Windows

```powershell
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Swagger UI:

- `http://localhost:8081/swagger-ui.html`

OpenAPI docs:

- `http://localhost:8081/v3/api-docs`

## Endpoints principales

### Autenticación

- `POST /api/v1/auth/login`
  - Request body:
    ```json
    {
      "email": "admin@example.com",
      "password": "password"
    }
    ```
  - Response:
    ```json
    {
      "access_token": "...",
      "refresh_token": "..."
    }
    ```

### Tarea

- `GET /api/v1/tarea/{tareaId}` - Obtener tarea por ID.
- `GET /api/v1/tarea/categoria/{categoria}` - Obtener tareas por categoría.
- `GET /api/v1/tarea/estado/{estado}` - Obtener tareas por estado.
- `GET /api/v1/tarea/{tareaId}/votos/count` - Contar votos de una tarea.
- `GET /api/v1/tarea/categoria/{categoria}/count` - Contar tareas por categoría.
- `PUT /api/v1/tarea` - Actualizar estado de tarea.
- `POST /api/v1/tarea/{tareaId}/usuarios/{usuarioId}/votar` - Agregar voto.
- `POST /api/v1/tarea/{tareaId}/usuarios/{usuarioId}/quitarVoto` - Quitar voto.
- `POST /api/v1/tarea` - Crear nueva tarea.

### Comentario

- `POST /api/v1/comentario` - Crear comentario.
- `GET /api/v1/comentario/{tareaId}` - Obtener comentarios de una tarea.

## Modelos de datos

### Entidades

- `Usuario`
  - `userId` (`Long`)
  - `nombre`
  - `apellido`
  - `rol` (`ADMINISTRADOR`, `PROGRAMADOR`)
  - `email`
  - `password`

- `Tarea`
  - `tareaId` (`Long`)
  - `titulo`
  - `descripcion`
  - `estado` (`OPEN`, `INPROGRESS`, `COMPLETED`)
  - `categoria` (`UI`, `UX`, `FEATURE`, `BUG`, `PERFORMANCE`)
  - `fechaCreacion`
  - `usuariosVotantes` (`ManyToMany` con `Usuario`)
  - `comentarios` (`OneToMany` con `Comentario`)

- `Comentario`
  - `comentarioId` (`Long`)
  - `comentario`
  - `tarea` (`ManyToOne`)
  - `usuario` (`ManyToOne`)

- `Token`
  - `id` (`Integer`)
  - `token`
  - `tokenType`
  - `revoked`
  - `expired`
  - `usuario` (`ManyToOne`)

### DTOs

- `LoginRequestDTO`
  - `email`
  - `password`
- `TokenResponseDTO`
  - `accessToken`
  - `refreshToken`
- `TareaDTO`
  - `titulo`
  - `descripcion`
  - `estado`
  - `categoria`
- `ActualizarEstadoTareaDTO`
  - `tareaId`
  - `estado`
- `ComentarioDTO`
  - `comentario`
  - `tarea`
  - `usuario`

## Seguridad y autenticación

- `JwtService` genera/valida JWT usando `jwt.secret`.
- `JwtAuthenticationFilter` extrae el token del header `Authorization: Bearer ...`.
- Los tokens también se guardan en BD y se validan en `TokenRepository` para que no estén expirados ni revocados.
- `SecurityConfig` permite acceso público a:
  - `/api/v1/auth/**`
  - `/v3/api-docs/**`
  - `/swagger-ui/**`
  - `/swagger-ui.html`
  - `/monitor/**`
  - `/ws-notificaciones/**`
- Todas las demás rutas requieren autenticación.

## Manejo global de respuestas y errores

- `GlobalResponseHandler` envuelve la mayoría de las respuestas en `ApiResponseDTO` con atributos:
  - `timestamp`
  - `status`
  - `message`
  - `data`
- `GlobalExceptionHandler` transforma:
  - `MethodArgumentNotValidException` en respuestas `400 Bad Request` con detalles de validación.
  - `RuntimeException` en respuestas `404 Not Found` con mensaje de error.

## Observaciones importantes

- `ComentarioController` actualmente importa `io.swagger.v3.oas.annotations.parameters.RequestBody` en lugar de `org.springframework.web.bind.annotation.RequestBody`.
  - Esto debe corregirse para que el JSON del request body se enlace correctamente.
- `application.properties` usa `spring.jpa.hibernate.ddl-auto=update`, conveniente en desarrollo pero revisar antes de producción.
- El JWT secret está en texto plano; mejor cargarlo por variable de entorno o servicio de secretos.

## Recomendaciones

1. Verificar que `application.properties` apunte a la base de datos correcta.
2. Ajustar el import de `@RequestBody` en `ComentarioController`.
3. Revisar y documentar los permisos del rol `ADMINISTRADOR` vs `PROGRAMADOR` si se agregan más reglas.
4. Usar `ApiResponseDTO` para respuestas consistentes en toda la API.


