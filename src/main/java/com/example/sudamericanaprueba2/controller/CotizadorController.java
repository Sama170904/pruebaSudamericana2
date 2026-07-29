package com.example.sudamericanaprueba2.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Response.CotizacionDTO;
import com.example.sudamericanaprueba2.service.CotizadorService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/cotizador")
public class CotizadorController {

    private final CotizadorService cotizadorService;

    @GetMapping("/conector/{conectorId}")
    public CotizacionDTO cotizar(
            @PathVariable("conectorId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long conectorId,
            @RequestParam("kwh") Double kwh) {
        return cotizadorService.cotizar(conectorId, kwh);
    }
}
