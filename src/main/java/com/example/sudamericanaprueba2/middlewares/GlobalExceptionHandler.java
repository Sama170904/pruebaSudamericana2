package com.example.sudamericanaprueba2.middlewares;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    // Este método atrapa los errores cuando fallan las validaciones del DTO (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        // Buscamos qué campos fallaron y qué mensaje tienen
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST); 
    }

    // Este método atrapa tus throw new RuntimeException("mensaje")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarEstudianteNoEncontrado(RuntimeException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage()); 
        
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND); 
    }

    // Atrapa bloqueos de Spring Security por falta de permisos (Roles)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> manejarAccesoDenegado(AccessDeniedException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Acceso Denegado");
        respuesta.put("detalle", "No tienes permisos para ejecutar esta acción");
        
        return new ResponseEntity<>(respuesta, HttpStatus.FORBIDDEN); // Devuelve el 403 real
    }

    // Atrapa errores cuando el JSON enviado está mal escrito o tiene un formato inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Formato inválido");
        respuesta.put("detalle", "Revisa que el JSON enviado esté correctamente escrito");
        
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST); 
    }
}