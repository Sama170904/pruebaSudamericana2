package com.example.sudamericanaprueba2.dto.Create;

import com.example.sudamericanaprueba2.entity.Tarea.Estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // me dio un error el swagger por falta de cosntructor en el dto y tuve que poenr esto 
@AllArgsConstructor
public class ActualizarEstadoTareaDTO {
    @NotNull
    private Long tareaId;

    @NotNull
    private Estado estado;


}
