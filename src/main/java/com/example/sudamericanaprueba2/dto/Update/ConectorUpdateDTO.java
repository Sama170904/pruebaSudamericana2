package com.example.sudamericanaprueba2.dto.Update;

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
public class ConectorUpdateDTO {

    @NotNull(message = "El ID del conector es obligatorio")
    @Min(value = 1, message = "El ID debe ser mayor a 0")
    private Long conectorId;

    private String tipo;

    @Min(value = 1, message = "La potencia debe ser mayor a 0")
    private Double potencia;

    @Min(value = 0, message = "La tarifa no puede ser negativa")
    private Double tarifa;
}
