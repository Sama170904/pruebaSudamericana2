package com.example.sudamericanaprueba2.dto.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long userId;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
}
