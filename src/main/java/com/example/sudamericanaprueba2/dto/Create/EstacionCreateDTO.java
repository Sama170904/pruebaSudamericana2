package com.example.sudamericanaprueba2.dto.Create;

import org.springframework.data.geo.Point;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstacionCreateDTO {

    @NotBlank(message = "El nombre de la estación es obligatorio")
    private String nombre;

    @NotNull(message = "La ubicación es obligatoria")
    private Point ubicacion;
}
