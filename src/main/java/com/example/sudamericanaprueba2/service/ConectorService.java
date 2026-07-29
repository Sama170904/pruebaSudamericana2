package com.example.sudamericanaprueba2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Create.ConectorCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.ConectorUpdateDTO;
import com.example.sudamericanaprueba2.entity.Conector;
import com.example.sudamericanaprueba2.repository.ConectorRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConectorService {

    private final ConectorRepository conectorRepository;

    public Conector getConector(Long id) {
        return conectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conector no encontrado con el ID: " + id));
    }

    public Page<Conector> getConectores(Pageable pageable) {
        return conectorRepository.findAll(pageable);
    }

    public List<Conector> getConectoresDisponiblesPorEstacion(Long estacionId) {
        return conectorRepository.findByEstaciones_EstacionIdAndDisponibleTrue(estacionId);
    }

    public Conector create(ConectorCreateDTO dto) {
        Conector conectorNuevo = Conector.builder()
                .tipo(dto.getTipo().trim())
                .potencia(dto.getPotencia())
                .tarifa(dto.getTarifa())
                .disponible(true)
                .build();
        return conectorRepository.save(conectorNuevo);
    }

    public Conector update(ConectorUpdateDTO dto) {
        Conector conectorExistente = conectorRepository.findById(dto.getConectorId())
                .orElseThrow(() -> new RuntimeException("Conector no encontrado con el ID: " + dto.getConectorId()));

        if (dto.getTipo() != null && !dto.getTipo().isBlank()) {
            conectorExistente.setTipo(dto.getTipo().trim());
        }
        if (dto.getPotencia() != null) {
            conectorExistente.setPotencia(dto.getPotencia());
        }
        if (dto.getTarifa() != null) {
            conectorExistente.setTarifa(dto.getTarifa());
        }

        return conectorRepository.save(conectorExistente);
    }

    public void delete(Long id) {
        if (!conectorRepository.existsById(id)) {
            throw new RuntimeException("Conector no encontrado con el ID: " + id);
        }
        conectorRepository.deleteById(id);
    }
}
