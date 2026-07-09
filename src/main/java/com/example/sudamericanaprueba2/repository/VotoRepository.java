package com.example.sudamericanaprueba2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByTarea_TareaIdAndUsuario_UserId(Long tareaId, Long userId);
    Boolean existsByTarea_TareaIdAndUsuario_UserId(Long tareaId, Long usuarioId);
    List<Voto> findByTarea_TareaId(Long tareaId);
    void deleteByTarea_TareaIdAndUsuario_UserId(Long tareaId, Long usuarioId);
}