package com.example.sudamericanaprueba2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.sudamericanaprueba2.dto.Create.EstacionCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.EstacionUpdateDTO;
import com.example.sudamericanaprueba2.entity.Conector;
import com.example.sudamericanaprueba2.entity.Estacion;
import com.example.sudamericanaprueba2.repository.ConectorRepository;
import com.example.sudamericanaprueba2.repository.EstacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstacionService {

    private final EstacionRepository estacionRepository;
    private final ConectorRepository conectorRepository;

    public Estacion asociarConectores(Long estacionId, List<Long> conectoresIds) {
        Estacion estacion = estacionRepository.findById(estacionId)
                .orElseThrow(() -> new RuntimeException("Estación no encontrada con el ID: " + estacionId));

        List<Conector> conectoresEncontrados = conectorRepository.findAllById(conectoresIds);
        if (conectoresEncontrados.isEmpty()) {
            throw new RuntimeException("No se encontraron conectores válidos para los IDs especificados");
        }

        estacion.setConectores(conectoresEncontrados);
        return estacionRepository.save(estacion);
    }

    public Estacion getEstacion(Long id) {
        return estacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estación no encontrada con el ID: " + id));
    }

    public Page<Estacion> getEstaciones(Pageable pageable) {
        return estacionRepository.findAll(pageable);
    }

    public Estacion create(EstacionCreateDTO dto) {
        Point loc = dto.getUbicacion() != null ? dto.getUbicacion() : new Point(-79.8862, 2.1962);
        Estacion estacionNueva = Estacion.builder()
                .nombre(dto.getNombre().trim())
                .ubicacion(loc)
                .estado(Estacion.Estado.ACTIVO)
                .build();
        return estacionRepository.save(estacionNueva);
    }

    public Estacion update(EstacionUpdateDTO dto) {
        Estacion estacionExistente = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new RuntimeException("Estación con ID " + dto.getEstacionId() + " no encontrada"));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            estacionExistente.setNombre(dto.getNombre().trim());
        }
        if (dto.getUbicacion() != null) {
            estacionExistente.setUbicacion(dto.getUbicacion());
        }
        return estacionRepository.save(estacionExistente);
    }

    public void delete(Long id) {
        if (!estacionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la Estacion con ID: " + id);
        }
        estacionRepository.deleteById(id);
    }
}
