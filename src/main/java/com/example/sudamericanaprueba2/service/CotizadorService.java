package com.example.sudamericanaprueba2.service;

import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Response.CotizacionDTO;
import com.example.sudamericanaprueba2.entity.Conector;
import com.example.sudamericanaprueba2.repository.ConectorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotizadorService {

    private final ConectorRepository conectorRepository;

    public CotizacionDTO cotizar(Long conectorId, Double consumoEnergiaKwh) {
        Conector conector = conectorRepository.findById(conectorId)
                .orElseThrow(() -> new RuntimeException("Conector no encontrado con el ID: " + conectorId));

        if (consumoEnergiaKwh == null || consumoEnergiaKwh < 0) {
            throw new RuntimeException("El consumo de energía en kWh debe ser un valor mayor o igual a 0");
        }

        if (conector.getTarifa() == null) {
            throw new RuntimeException("El conector seleccionado no tiene una tarifa asignada");
        }

        double tarifa = conector.getTarifa();
        double costoEstimado = consumoEnergiaKwh * tarifa;

        return CotizacionDTO.builder()
                .conectorId(conector.getConectorId())
                .tipoConector(conector.getTipo())
                .potenciaKw(conector.getPotencia())
                .tarifaKwh(tarifa)
                .energiaEstimadaKwh(consumoEnergiaKwh)
                .costoEstimadoUSD(costoEstimado)
                .build();
    }
}
