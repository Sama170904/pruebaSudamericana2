package com.example.sudamericanaprueba2.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Create.EstacionCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.EstacionUpdateDTO;
import com.example.sudamericanaprueba2.entity.Estacion;
import com.example.sudamericanaprueba2.service.EstacionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/estacion")
public class EstacionController {

    private final EstacionService estacionService;

    @GetMapping
    public Page<Estacion> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "estacionId") Pageable pageable) {
        return estacionService.getEstaciones(pageable);
    }

    @GetMapping("/{estacionId}")
    public Estacion getById(@PathVariable("estacionId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long estacionId) {
        return estacionService.getEstacion(estacionId);
    }

    @PostMapping
    public Estacion create(@RequestBody @Valid EstacionCreateDTO estacion) {
        return estacionService.create(estacion);
    }

    @PutMapping
    public Estacion update(@RequestBody @Valid EstacionUpdateDTO estacion) {
        return estacionService.update(estacion);
    }

    @PutMapping("/{estacionId}/conectores")
    public Estacion asociarConectores(
            @PathVariable("estacionId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long estacionId,
            @RequestBody List<Long> conectoresIds) {
        return estacionService.asociarConectores(estacionId, conectoresIds);
    }

    @DeleteMapping("/{estacionId}")
    public void delete(@PathVariable("estacionId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long estacionId) {
        estacionService.delete(estacionId);
    }
}
