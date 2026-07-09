package com.example.sudamericanaprueba2.dto.Create;

import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Tarea.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor // me dio un error el swagger por falta de cosntructor en el dto y tuve que poenr esto 
@AllArgsConstructor
public class TareaCreateDTO {


    @NotBlank(message="Debe ingresar un titulo") 
    private String titulo;


    @NotBlank(message="Debe ingresar una descripcion")
    private String descripcion;

    @Pattern(regexp = "^(PERFORMANCE|UX|UI|FEATURE|BUG)$", message = "La categoría debe ser PERFORMANCE, UX, UI, FEATURE o BUG")
    @NotNull(message="Debe ingresar una categoria")
    private String categoria;

}
