# Sudamericana Prueba 2

API REST desarrollada en Spring Boot para gestionar tareas, comentarios, votos y autenticación con JWT.

## Descripción general

Este proyecto implementa un backend con:

- Registro y autenticación de usuarios.
- Gestión de tareas con estados y categorías.
- Sistema de votos por tarea.
- Comentarios asociados a tareas y usuarios.
- Protección de rutas mediante JWT y Spring Security.
- Documentación automática con Swagger / OpenAPI.

## Tecnologías utilizadas

- Java 17
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Validation
- JWT (jjwt)
- Lombok
- Swagger UI / OpenAPI
- SQL Server (configuración actual)

## Estructura del proyecto

- src/main/java/com/example/sudamericanaprueba2
    - config/: configuración de seguridad, autenticación y OpenAPI.
    - controller/: controladores REST para auth, tareas, comentarios y votos.
    - dto/: DTOs para solicitudes y respuestas.
    - entity/: entidades JPA.
    - filter/: filtro JWT.
    - middlewares/: manejo global de excepciones y respuestas.
    - repository/: repositorios Spring Data JPA.
    - service/: lógica de negocio.
- src/main/resources/application.properties: configuración principal de la aplicación.

## Requisitos previos

- Java 17 o superior.
- Maven.
- Base de datos SQL Server en localhost:1433.
- Opcional: Swagger UI para probar la API.

## Configuración

El archivo principal de configuración es:

- src/main/resources/application.properties

Configuración actual usada por la app:

- spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=sudamericanaprueba2;encrypt=true;trustServerCertificate=true;
- spring.datasource.username=sa
- spring.datasource.password=root
- spring.jpa.hibernate.ddl-auto=update
- server.port=8081
- springdoc.api-docs.path=/v3/api-docs
- springdoc.swagger-ui.path=/swagger-ui.html
- jwt.secret=TWlDbGF2ZVN1cGVyU2VjcmV0YVBhcmFDcnVkUmFwaWRvMjAyNiEhIQ==
- jwt.expiration=3600000
- jwt.refresh.expiration=604800000

> Recomendación: no dejar credenciales ni secretos en texto plano en un entorno de producción.

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

Una vez iniciada, la aplicación estará disponible en:

- http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html
- OpenAPI docs: http://localhost:8081/v3/api-docs

## Endpoints principales

### Autenticación

- POST /api/v1/auth/login
    - Inicia sesión y devuelve tokens JWT.
    - Body esperado (LoginRequestDTO):

```json
{
    "email": "usuario@example.com",
    "password": "password"
}
```

- Respuesta (TokenResponseDTO):

```json
{
    "access_token": "<jwt-access-token>",
    "refresh_token": "<jwt-refresh-token>"
}
```

> Importante: el backend espera la ruta exacta `/api/v1/auth/login` y devuelve los campos `access_token` y `refresh_token`. Si el frontend usa otra ruta o espera un campo `token`, la autenticación no funcionará correctamente.

### Tareas

- GET /api/v1/tarea/{tareaId}
    - Obtiene una tarea por su ID.
- GET /api/v1/tarea/categoria/{categoria}
    - Lista tareas filtradas por categoría.
- GET /api/v1/tarea/estado/{estado}
    - Lista tareas filtradas por estado.
- GET /api/v1/tarea/categoria/{estado}/count
    - Cuenta tareas por estado.
- PUT /api/v1/tarea
    - Actualiza el estado de una tarea usando ActualizarEstadoTareaDTO.
    - Body esperado:

```json
{
    "tareaId": 1,
    "estado": "EN_PROCESO"
}
```

- POST /api/v1/tarea
    - Crea una nueva tarea (requiere rol ADMINISTRADOR).
    - Body esperado (TareaCreateDTO):

```json
{
    "titulo": "Nueva tarea",
    "descripcion": "Descripción de la tarea",
    "categoria": "FEATURE"
}
```

### Comentarios

- POST /api/v1/comentario
    - Crea un comentario para una tarea.
    - Body esperado (ComentarioCreateDTO):

```json
{
    "comentario": "Texto del comentario",
    "tarea": 1,
    "usuario": 2
}
```

- GET /api/v1/comentario/{tareaId}
    - Obtiene los comentarios asociados a una tarea.

### Votos

- GET /api/v1/voto/{tareaId}/votos/count
    - Cuenta los votos de una tarea.
- POST /api/v1/voto
    - Crea un voto para una tarea.
    - Body esperado (VotoCreateDTO):

```json
{
    "idUsuario": 2,
    "idTarea": 1
}
```

- DELETE /api/v1/voto/{tareaId}/usuario/{usuarioId}
    - Elimina el voto de un usuario para una tarea.

## DTOs principales

- LoginRequestDTO
    - email: String, obligatorio y con formato válido.
    - password: String, obligatorio.
- TokenResponseDTO
    - access_token: String.
    - refresh_token: String.
- TareaCreateDTO
    - titulo: String, obligatorio.
    - descripcion: String, obligatorio.
    - categoria: String, obligatorio. Valores válidos: PERFORMANCE, UX, UI, FEATURE, BUG.
- ActualizarEstadoTareaDTO
    - tareaId: Long, obligatorio.
    - estado: Enum Estado, obligatorio.
- ComentarioCreateDTO
    - comentario: String, obligatorio.
    - tarea: Long, ID de tarea, obligatorio.
    - usuario: Long, ID de usuario, obligatorio.
- VotoCreateDTO
    - idUsuario: Long, obligatorio.
    - idTarea: Long, obligatorio.

## Cómo se construye el JWT

El servicio `JwtService` crea dos tipos de tokens:

- access token: expiración configurada en `jwt.expiration`.
- refresh token: expiración configurada en `jwt.refresh.expiration`.

El token se arma con `Jwts.builder()` y contiene:

- `sub` (subject): el username del usuario, que en este proyecto corresponde al email.
- `iat` (issued at): fecha de emisión.
- `exp` (expiration): fecha de expiración.
- `roles`: lista de roles del usuario. Actualmente el valor puede ser `ROLE_ADMINISTRADOR` o `ROLE_PROGRAMADOR`.

El JWT se firma con la clave secreta `jwt.secret`, que se decodifica desde Base64 y se usa como HMAC SHA-256.

### Validación del JWT

Para validar un token, el servicio:

1. Decodifica el JWT con la misma clave secreta.
2. Extrae el username del claim `sub`.
3. Compara el username extraído con el `UserDetails` actual.
4. Verifica que la fecha de expiración no haya pasado.

Esto significa que, en el cuerpo del JWT, la información del usuario es mínima y está centrada en el identificador principal (`subject`) y los roles, sin incluir datos sensibles como la contraseña.

## Seguridad

- La mayoría de las rutas están protegidas con JWT.
- Los endpoints de autenticación y documentación están abiertos para permitir el acceso inicial.
- El filtro JWT se aplica antes del filtro de autenticación por defecto de Spring Security.
- Los tokens se gestionan con soporte de base de datos y validación de expiración/revocación.

## Modelos principales

### Entidades

- Usuario
    - userId
    - nombre
    - apellido
    - email
    - password
    - rol

- Tarea
    - tareaId
    - titulo
    - descripcion
    - estado
    - categoria
    - fechaCreacion

- Comentario
    - comentarioId
    - comentario
    - tarea
    - usuario

- Voto
    - id
    - tarea
    - usuario

## Observaciones

- El proyecto usa actualización automática del esquema con spring.jpa.hibernate.ddl-auto=update, útil para desarrollo.
- La documentación está disponible en Swagger y puede usarse como referencia rápida para probar los endpoints.
- Si se necesita una versión más robusta para producción, conviene separar secretos, mejorar manejo de errores y añadir pruebas automatizadas.
