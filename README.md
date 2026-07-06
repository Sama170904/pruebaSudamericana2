# Sudamericanaprueba2

Proyecto Spring Boot simple para gestionar tareas, usuarios y comentarios.

## Descripción

API backend en Java (Spring Boot) que modela las entidades principales: `Usuario`, `Tarea` y `Comentario`. Incluye DTOs con validaciones, repositorios JPA y un manejador global de excepciones para las validaciones.

## Requisitos

- Java 17+ (o la versión configurada en el proyecto)
- Maven (se incluye `mvnw` / `mvnw.cmd` como wrapper)
- SQL Server en `localhost:1433` (o ajustar `application.properties`)

## Ejecutar el proyecto (Windows)

Construir y ejecutar usando el wrapper:

```bash
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

O ejecutar el JAR generado:

```bash
mvnw.cmd clean package
java -jar target/*.jar
```

El servidor corre por defecto en el puerto `8081`.

## Estructura principal

- `src/main/java/com/example/sudamericanaprueba2`
  - `Sudamericanaprueba2Application.java`: clase principal Spring Boot.
  - `dto/`: clases DTO usadas en peticiones/respuestas.
    - `TareaDTO.java` - DTO para crear/editar tareas.
    - `ComentarioDTO.java` - DTO para crear comentarios.
  - `entity/`: entidades JPA.
    - `Usuario.java` - entidad usuario con campos: `userId`, `nombre`, `apellido`, `rol`, `email`, `password`, relaciones con `Tarea` y `Comentario`.
    - `Tarea.java` - entidad tarea con campos: `tareaId`, `titulo`, `descripcion`, `estado` (enum: `OPEN`, `INPROGRESS`, `COMPLETED`), `categoria` (enum: `UI`, `UX`, `FEATURE`, `BUG`, `PERFORMANCE`), relación ManyToMany con `Usuario` y OneToMany con `Comentario`.
    - `Comentario.java` - entidad comentario con campos: `comentarioId`, `comentario`, ManyToOne a `Tarea` y `Usuario`.
  - `repository/`: repositorios JPA.
    - `UsuarioRepository.java` - extiende `JpaRepository`, con `findByEmail(String)`.
    - `TareaRepository.java` - extiende `JpaRepository`.
    - `ComentarioRepository.java` - extiende `JpaRepository`.
  - `middlewares/GlobalExceptionHandler.java`: captura `MethodArgumentNotValidException` y `RuntimeException` para devolver mensajes adecuados.

## DTOs y validaciones

- `TareaDTO`:
  - `titulo` (String) - `@NotBlank`
  - `descripcion` (String) - `@NotBlank`
  - `estado` (enum) - `@NotNull`
  - `categoria` (enum) - `@NotNull`

- `ComentarioDTO`:
  - `comentario` (String) - `@NotBlank`
  - `tarea` (int) - actualmente anotado con `@NotBlank` (se espera un id numérico)
  - `usuario` (int) - actualmente anotado con `@NotBlank` (se espera un id numérico)

Nota: las anotaciones en `ComentarioDTO` usan `@NotBlank` sobre campos numéricos (`int`), lo cual no es correcto; debería usarse `@NotNull` y/o tipos envoltorio (`Integer`) si se quiere validar nulos.

## Observaciones importantes

- Tipos de ID en entidades vs. repositorios: las entidades usan `Long` para los IDs (`tareaId`, `comentarioId`, `userId`), pero los repositorios están parametrizados con `Integer` (por ejemplo `JpaRepository<Comentario, Integer>`). Esto provocará errores en tiempo de compilación/ejecución y debería corregirse a `Long` en los repositorios.
- No se encontraron controladores (`controller`) en el árbol de código leído; por lo tanto no hay documentación de endpoints REST específica en este README.

## Configuración (application.properties)

- Base de datos: `spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=sudamericanaprueba2;...`
- Usuario/clave por defecto en archivo: `sa` / `root` (ajustar en entornos reales).
- `spring.jpa.hibernate.ddl-auto=update` (útil para desarrollo; revisar antes de producción).
- Puerto: `server.port=8081`.
- JWT: `jwt.secret` y tiempos de expiración configurados.

## Recomendaciones / siguientes pasos

1. Corregir la inconsistencia de tipos de ID en los repositorios (usar `Long`).
2. Ajustar validaciones en `ComentarioDTO` (usar `Integer`/`Long` y `@NotNull`).
3. Agregar controladores REST y documentar los endpoints (o indicar si ya existen en otra rama).
4. Externalizar secretos (no mantener `jwt.secret` en texto plano en repositorios públicos).


