package com.example.sudamericanaprueba2.dto.Response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class ApiResponseDTO<T> {
    
    private LocalDateTime timestamp; 
    private int status;             
    private String message;         
    private T data;                 
}