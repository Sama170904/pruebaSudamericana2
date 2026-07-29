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

import com.example.sudamericanaprueba2.dto.Create.ConectorCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.ConectorUpdateDTO;
import com.example.sudamericanaprueba2.entity.Conector;
import com.example.sudamericanaprueba2.service.ConectorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/conector")
public class ConectorController {

    private final ConectorService conectorService;

    @GetMapping
    public Page<Conector> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "conectorId") Pageable pageable) {
        return conectorService.getConectores(pageable);
    }

    @GetMapping("/{conectorId}")
    public Conector getById(@PathVariable("conectorId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long conectorId) {
        return conectorService.getConector(conectorId);
    }

    @GetMapping("/estacion/{estacionId}/disponibles")
    public List<Conector> getDisponiblesPorEstacion(
            @PathVariable("estacionId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long estacionId) {
        return conectorService.getConectoresDisponiblesPorEstacion(estacionId);
    }

    @PostMapping
    public Conector create(@RequestBody @Valid ConectorCreateDTO conector) {
        return conectorService.create(conector);
    }

    @PutMapping
    public Conector update(@RequestBody @Valid ConectorUpdateDTO conector) {
        return conectorService.update(conector);
    }

    @DeleteMapping("/{conectorId}")
    public void delete(@PathVariable("conectorId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long conectorId) {
        conectorService.delete(conectorId);
    }
}
