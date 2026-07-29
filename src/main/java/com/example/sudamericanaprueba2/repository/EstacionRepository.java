package com.example.sudamericanaprueba2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Estacion;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
}
