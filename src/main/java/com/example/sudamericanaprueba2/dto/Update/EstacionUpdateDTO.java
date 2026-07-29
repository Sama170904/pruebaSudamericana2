package com.example.sudamericanaprueba2.dto.Update;

import org.springframework.data.geo.Point;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstacionUpdateDTO {

    @NotNull
    @Min(value = 1, message = "El ID debe ser mayor a 0")
    private Long estacionId;

    private String nombre;

    private Point ubicacion;
}
