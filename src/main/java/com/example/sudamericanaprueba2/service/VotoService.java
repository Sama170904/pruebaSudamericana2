package com.example.sudamericanaprueba2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Create.VotoCreateDTO;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.entity.Voto;
import com.example.sudamericanaprueba2.repository.TareaRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;
import com.example.sudamericanaprueba2.repository.VotoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    //asignar voto a una tarea
    public Voto create(VotoCreateDTO voto) {
        Usuario usuario = usuarioRepository.findById(voto.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("usuario no encontrado con ID: "+voto.getIdUsuario()));
        Tarea tarea = tareaRepository.findById(voto.getIdTarea())
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: "+voto.getIdTarea()));
        Voto votoNuevo = Voto.builder()
            .usuario(usuario)
            .tarea(tarea)
            .build();
        votoRepository.save(votoNuevo);
        return votoNuevo;
    }


    public void delete(Long tareaId, Long usuarioId) {
        Voto votoFinal = votoRepository.findByTarea_TareaIdAndUsuario_UserId(tareaId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Voto no encontrado, trate con un id diferente"));
        
        votoRepository.deleteById(votoFinal.getVotoId());
    }

    //contar votos por tarea
    public int contarVotos(Long tareaId){
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("tarea no encontrada con ID: " + tareaId));
        List<Voto> votos = votoRepository.findByTarea_TareaId(tareaId);
        int cantidadDeVotos = votos.size();
        return cantidadDeVotos;
    }
}
