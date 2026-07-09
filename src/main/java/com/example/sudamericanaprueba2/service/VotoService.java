package com.example.sudamericanaprueba2.service;

import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.VotoCreateDTO;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.entity.Voto;
import com.example.sudamericanaprueba2.repository.VotoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;

    //asignar voto a una tarea
    public Voto create(VotoCreateDTO voto) {
        Voto votoNuevo = Voto.builder()
            .usuario(voto.getIdTarea())
            .tarea(voto.getIdUsuario())
            .build();
        votoRepository.save(votoNuevo);
        return votoNuevo;
    }

    //quitar voto a una tarea
    public void delete(Long tareaId, Long usuarioId) {
        if (!votoRepository.exitsByTarea_TareaIdAndUsuario_UserId(tareaId, usuarioId)) {
            throw new RuntimeException("No hay un voto asignado a esa tarea con ese usuario");
        }
        votoRepository.findByTarea_TareaIdAndUsuario_UserId(tareaId, usuarioId);
    }

    //contar votos por tarea
    public int contarVotos(Long tareaId){
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));
        int cantidadDeVotos = tarea.getUsuariosVotantes().size();
        return cantidadDeVotos;
    }
}
