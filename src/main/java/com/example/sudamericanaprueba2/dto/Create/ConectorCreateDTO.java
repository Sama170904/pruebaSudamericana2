package com.example.sudamericanaprueba2.dto.Create;

import jakarta.validation.constraints.Min;
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
public class ConectorCreateDTO {

    @NotBlank(message = "El tipo de conector es obligatorio")
    private String tipo;

    @NotNull(message = "La potencia es obligatoria")
    @Min(value = 1, message = "La potencia debe ser mayor a 0")
    private Double potencia;

    @NotNull(message = "La tarifa es obligatoria")
    @Min(value = 0, message = "La tarifa no puede ser negativa")
    private Double tarifa;
}
