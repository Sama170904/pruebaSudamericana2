package com.example.sudamericanaprueba2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.ComentarioDTO;
import com.example.sudamericanaprueba2.dto.TareaDTO;
import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.repository.ComentarioRepository;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository; 
    private final UsuarioRepository usuarioRepository;    
    private final TareaRepository tareaRepository; 
    
public Comentario create(ComentarioDTO comentario) {
    
    Tarea tareaObjeto = tareaRepository.findById(comentario.getTarea())
        .orElseThrow(() -> new RuntimeException("La tarea no existe"));
        
    Usuario usuarioObjeto = usuarioRepository.findById(comentario.getUsuario())
        .orElseThrow(() -> new RuntimeException("El usuario no existe"));

    Comentario comentarioNuevo = Comentario.builder()
        .comentario(comentario.getComentario())
        .tarea(tareaObjeto)    
        .usuario(usuarioObjeto) 
        .build();
        
    comentarioRepository.save(comentarioNuevo);
    return comentarioNuevo;
}

    public List<Comentario> getComentariosPorTarea(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));
        return comentarioRepository.findByTarea(tarea);
    }

    


}
