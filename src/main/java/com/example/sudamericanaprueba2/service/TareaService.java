package com.example.sudamericanaprueba2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.ActualizarEstadoTareaDTO;
import com.example.sudamericanaprueba2.dto.TareaDTO;
import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Tarea.Estado;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.Builder;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository; 
    private final UsuarioRepository usuarioRepository; 
    
    //Crear tarea
    public Tarea create(TareaDTO tarea) {
        Tarea tareaNueva = Tarea.builder()
            .titulo(tarea.getTitulo().toUpperCase())
            .descripcion(tarea.getDescripcion())
            .estado(tarea.getEstado())
            .categoria(tarea.getCategoria())
            .build();
        tareaRepository.save(tareaNueva);
        return tareaNueva;
    }

    //Buscar Tareas por Categoria
    public List<Tarea> getTareaPorCategoria(Categoria categoria) {
        return tareaRepository.findByCategoria(categoria);
    }
    
    //Buscar Tareas por estado
    public List<Tarea> getTareaPorEstado(Estado estado) {
        return tareaRepository.findByEstado(estado);
    }

    //buscar tarea por ID
    public Tarea getTareaId(Long id) {
        return tareaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    //Cambiar estado de la tarea
    public Tarea cambiarEstadoTarea(ActualizarEstadoTareaDTO tarea){
        Tarea TareaExistente = tareaRepository.findById(tarea.getTareaId())
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        TareaExistente.setEstado(tarea.getEstado());
        tareaRepository.save(TareaExistente);
        return TareaExistente;
    }

    //asignar voto a una tarea
    public Tarea asignarVoto(Long tareaId, Long usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("usuario no encontrado con ID: " + tareaId));

        tarea.getUsuariosVotantes().add(usuario);
        return tareaRepository.save(tarea);
    }

    //quitar voto a una tarea
    public Tarea quitarVoto(Long tareaId, Long usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("usuario no encontrado con ID: " + tareaId));

        tarea.getUsuariosVotantes().remove(usuario);
        return tareaRepository.save(tarea);
    }

    //contar votos por tarea
    public int contarVotos(Long tareaId){
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));
        int cantidadDeVotos = tarea.getUsuariosVotantes().size();
        return cantidadDeVotos;
    }

    //contar tareas por categoria
    public int contarPorCategoria(Categoria categoria){
        int NumeroTareasPorCategoria = this.getTareaPorCategoria(categoria).size();
        return NumeroTareasPorCategoria;
    }

    
}
