package com.example.sudamericanaprueba2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Comentario;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Tarea.Categoria;
import com.example.sudamericanaprueba2.entity.Tarea.Estado;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByCategoria(Categoria categoria);
    List<Tarea> findByEstado(Estado estado);
}