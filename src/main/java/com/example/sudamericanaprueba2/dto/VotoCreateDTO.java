package com.example.sudamericanaprueba2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class VotoCreateDTO {
    
    @NotNull(message="Debe ingresar un id de usuario")
    private Long idUsuario;

    @NotNull(message = "Debe ingresa un id de tarea")
    private Long idTarea;
}
