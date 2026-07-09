package com.example.sudamericanaprueba2.dto.Response;



import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Data
@Builder
public class ComentarioResponseDTO {
    

    private Long comentarioId;

    private String comentario;


    private Tarea tarea;


    private UsuarioResponseDTO usuario;
}
