package com.example.sudamericanaprueba2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sudamericanaprueba2.entity.Conector;

import java.util.List;

@Repository
public interface ConectorRepository extends JpaRepository<Conector, Long> {
    List<Conector> findByEstaciones_EstacionIdAndDisponibleTrue(Long estacionId);
}
