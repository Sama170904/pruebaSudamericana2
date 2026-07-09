package com.example.sudamericanaprueba2.dto;

import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor // me dio un error el swagger por falta de cosntructor en el dto y tuve que poenr esto 
@AllArgsConstructor
public class ComentarioCreateDTO {

    @NotBlank(message="No puede comentar en blanco")
    private String comentario;

    @NotNull(message="No un id de tarea asignado")
    private Long tarea;

    @NotNull(message="No hay un id de usuario asignado")
    private Long usuario;
}
