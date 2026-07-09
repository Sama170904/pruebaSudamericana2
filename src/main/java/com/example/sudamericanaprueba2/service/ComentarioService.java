package com.example.sudamericanaprueba2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Create.ComentarioCreateDTO;
import com.example.sudamericanaprueba2.dto.Create.TareaCreateDTO;
import com.example.sudamericanaprueba2.dto.Response.ComentarioResponseDTO;
import com.example.sudamericanaprueba2.dto.Response.UsuarioResponseDTO;
import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.repository.ComentarioRepository;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository; 
    private final UsuarioRepository usuarioRepository;    
    private final TareaRepository tareaRepository; 
    


public ComentarioResponseDTO create(ComentarioCreateDTO comentario) {
    
    Tarea tareaObjeto = tareaRepository.findById(comentario.getTarea())
        .orElseThrow(() -> new RuntimeException("La tarea no existe"));
        
    Usuario usuarioObjeto = usuarioRepository.findById(comentario.getUsuario())
        .orElseThrow(() -> new RuntimeException("El usuario no existe"));

    Comentario comentarioNuevo = Comentario.builder()
        .comentario(comentario.getComentario().trim())
        .tarea(tareaObjeto)    
        .usuario(usuarioObjeto) 
        .build();
        
    comentarioRepository.save(comentarioNuevo);

    UsuarioResponseDTO usuarioDTO = UsuarioResponseDTO.builder()
        .userId(usuarioObjeto.getUserId()) // Tomamos los datos del usuarioObjeto consultado arriba
        .nombre(usuarioObjeto.getNombre())
        .apellido(usuarioObjeto.getApellido())
        .build();

    ComentarioResponseDTO comentarioResponse = ComentarioResponseDTO.builder()
        .comentarioId(comentarioNuevo.getComentarioId())
        .comentario(comentarioNuevo.getComentario())
        .tarea(comentarioNuevo.getTarea())
        .usuario(usuarioDTO) // Aquí inyectamos el DTO del usuario que armamos en el paso 3
        .build();

    return comentarioResponse;
    }


public List<ComentarioResponseDTO> getComentariosPorTarea(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));
            
        List<Comentario> comentariosEntidad = comentarioRepository.findByTarea(tarea);
        
        return comentariosEntidad.stream().map(comentario -> {
            
            // A. Usamos 'comentario.getUsuario()' para obtener los datos del elemento actual
            UsuarioResponseDTO usuarioDTO = UsuarioResponseDTO.builder()
                .userId(comentario.getUsuario().getUserId()) 
                .nombre(comentario.getUsuario().getNombre())
                .apellido(comentario.getUsuario().getApellido())
                .build();
                
            // B. Retornamos el builder directamente usando la variable 'comentario'
            return ComentarioResponseDTO.builder()
                .comentarioId(comentario.getComentarioId())
                .comentario(comentario.getComentario())
                .tarea(comentario.getTarea())
                .usuario(usuarioDTO) 
                .build();
                
        }).collect(Collectors.toList()); 
    }

}




