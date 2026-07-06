package com.example.sudamericanaprueba2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
}
