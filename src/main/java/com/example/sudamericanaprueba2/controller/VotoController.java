package com.example.sudamericanaprueba2.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudamericanaprueba2.dto.Create.TareaCreateDTO;
import com.example.sudamericanaprueba2.dto.Create.VotoCreateDTO;
import com.example.sudamericanaprueba2.entity.Tarea;
import com.example.sudamericanaprueba2.entity.Voto;
import com.example.sudamericanaprueba2.service.VotoService;

import org.springframework.web.bind.annotation.RequestBody; 
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/voto")
public class VotoController {

    private final VotoService votoService;

    @GetMapping("/{tareaId}/votos/count")
    public int contarVotos(@PathVariable("tareaId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long id) {
        return votoService.contarVotos(id); 
    }

    @PostMapping
    public Voto votar(@RequestBody @Valid VotoCreateDTO voto) {
        return votoService.create(voto);
    }

    @DeleteMapping("/{tareaId}/usuario/{usuarioId}")
    public void delete(@PathVariable("tareaId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long tareaId,
                                    @PathVariable("usuarioId") @Min(value = 1, message = "El ID debe ser mayor a 0") Long usuarioId) {
        votoService.delete(tareaId, usuarioId);
    }  
}
