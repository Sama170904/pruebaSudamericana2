package com.example.sudamericanaprueba2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.entity.Tarea;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByTarea(Tarea tarea);
}
