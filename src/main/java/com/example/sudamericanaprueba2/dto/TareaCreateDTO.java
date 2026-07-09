package com.example.sudamericanaprueba2.dto;

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


    @NotNull(message="Debe ingresar una categoria")
    private Categoria categoria;

}
