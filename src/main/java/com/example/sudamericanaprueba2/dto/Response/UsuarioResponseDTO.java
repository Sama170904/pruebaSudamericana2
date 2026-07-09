package com.example.sudamericanaprueba2.dto.Response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {


    private Long userId;


    private String nombre;


    private String apellido;
}
