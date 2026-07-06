package com.example.sudamericanaprueba2.middlewares;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    //Este método atrapa los errores cuando fallan las validaciones del DTO (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        // Buscamos qué campos fallaron y qué mensaje tienen
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST); 
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarEstudianteNoEncontrado(RuntimeException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage()); 
        
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND); 
    }
}