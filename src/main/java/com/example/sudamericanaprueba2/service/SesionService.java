package com.example.sudamericanaprueba2.service;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sudamericanaprueba2.dto.Create.SesionCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.EstacionUpdateDTO;
import com.example.sudamericanaprueba2.dto.Update.SesionFinalizarDTO;
import com.example.sudamericanaprueba2.entity.Conector;
import com.example.sudamericanaprueba2.entity.Estacion;
import com.example.sudamericanaprueba2.entity.Sesion;
import com.example.sudamericanaprueba2.entity.Usuario;
import com.example.sudamericanaprueba2.repository.ConectorRepository;
import com.example.sudamericanaprueba2.repository.EstacionRepository;
import com.example.sudamericanaprueba2.repository.SesionRepository;
import com.example.sudamericanaprueba2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final EstacionRepository estacionRepository;
    private final ConectorRepository conectorRepository;
    private final UsuarioRepository usuarioRepository;

    public Sesion iniciar(SesionCreateDTO dto) {
        Estacion estacion = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new RuntimeException("La estación con ID " + dto.getEstacionId() + " no existe"));

        Conector conector = conectorRepository.findById(dto.getConectorId())
                .orElseThrow(() -> new RuntimeException("El conector con ID " + dto.getConectorId() + " no existe"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + dto.getUsuarioId() + " no existe"));

        if (Boolean.FALSE.equals(conector.getDisponible())) {
            throw new RuntimeException("El conector seleccionado ya se encuentra ocupado");
        }

        // Marcar el conector como ocupado al iniciar la carga
        conector.setDisponible(false);
        conectorRepository.save(conector);

        Sesion sesionNueva = Sesion.builder()
                .consumoEnergia(0.0)
                .costo(0.0)
                .estacion(estacion)
                .conector(conector)
                .usuarioId(usuario)
                .build();

        return sesionRepository.save(sesionNueva);
    }

    public Sesion finalizar(SesionFinalizarDTO dto) {
        Sesion sesionExistente = sesionRepository.findById(dto.getSesionId())
                .orElseThrow(() -> new RuntimeException("La sesión con ID " + dto.getSesionId() + " no existe"));

        LocalTime horaFin = dto.getHoraFin() != null ? dto.getHoraFin() : LocalTime.now();
        sesionExistente.setHoraFin(horaFin);

        // Calcular diferencia de tiempo en horas (minutos / 60.0)
        Duration duracion = Duration.between(sesionExistente.getHoraInicio(), horaFin);
        double horasCargadas = Math.max(0.0, duracion.toMinutes() / 60.0);

        Conector conector = sesionExistente.getConector();
        if (conector != null) {
            // Calcular energía consumida (Potencia kW * Horas)
            double potencia = conector.getPotencia() != null ? conector.getPotencia() : 0.0;
            double consumoEnergia = potencia * horasCargadas;
            sesionExistente.setConsumoEnergia(consumoEnergia);

            // Calcular costo total (Consumo kWh * Tarifa)
            double tarifa = conector.getTarifa() != null ? conector.getTarifa() : 0.0;
            double costoTotal = consumoEnergia * tarifa;
            sesionExistente.setCosto(costoTotal);

            // Liberar el conector para que vuelva a estar disponible
            conector.setDisponible(true);
            conectorRepository.save(conector);
        }

        // Marcar estado de la sesión como INACTIVO (finalizada)
        sesionExistente.setEstado(Sesion.Estado.INACTIVO);

        return sesionRepository.save(sesionExistente);
    }

    public Page<Sesion> getSesiones(Pageable pageable) {
        return sesionRepository.findAll(pageable);
    }

    public Sesion getSesion(Long id) {
        return sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada con el ID: " + id));
    }

    public Page<Sesion> getSesionesPorUsuario(Long userId, Pageable pageable) {
        return sesionRepository.findByUsuarioId_UserId(userId, pageable);
    }
}
