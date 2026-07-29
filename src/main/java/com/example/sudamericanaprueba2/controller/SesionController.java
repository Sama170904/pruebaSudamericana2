package com.example.sudamericanaprueba2.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Create.SesionCreateDTO;
import com.example.sudamericanaprueba2.dto.Update.SesionFinalizarDTO;
import com.example.sudamericanaprueba2.entity.Sesion;
import com.example.sudamericanaprueba2.service.SesionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/sesion")
public class SesionController {

    private final SesionService sesionService;

    @GetMapping
    public Page<Sesion> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "sesionId") Pageable pageable) {
        return sesionService.getSesiones(pageable);
    }

    @GetMapping("/{sesionId}")
    public Sesion getById(@PathVariable("sesionId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long sesionId) {
        return sesionService.getSesion(sesionId);
    }

    @GetMapping("/usuario/{userId}")
    public Page<Sesion> getByUsuario(
            @PathVariable("userId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long userId,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "sesionId") Pageable pageable) {
        return sesionService.getSesionesPorUsuario(userId, pageable);
    }

    @PostMapping("/iniciar")
    public Sesion iniciar(@RequestBody @Valid SesionCreateDTO sesion) {
        return sesionService.iniciar(sesion);
    }

    @PutMapping("/finalizar")
    public Sesion finalizar(@RequestBody @Valid SesionFinalizarDTO sesion) {
        return sesionService.finalizar(sesion);
    }
}
