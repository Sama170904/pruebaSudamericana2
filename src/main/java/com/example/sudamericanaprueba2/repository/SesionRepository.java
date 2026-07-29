package com.example.sudamericanaprueba2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Sesion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    Page<Sesion> findByUsuarioId_UserId(Long userId, Pageable pageable);
}
